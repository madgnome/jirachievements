package com.madgnome.jira.plugins.jirachievements.statistics;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.issue.changehistory.ChangeHistoryManager;
import com.atlassian.jira.jql.parser.JqlQueryParser;
import com.atlassian.jira.user.util.UserUtil;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.IProjectStatisticDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserStatisticDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;
import gnu.trove.TObjectIntHashMap;
import gnu.trove.TObjectIntProcedure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public abstract class AbstractStatisticCalculator implements IStatisticCalculator
{
  protected final static Logger logger = LoggerFactory.getLogger(AbstractStatisticCalculator.class);

  protected final JqlQueryParser jqlQueryParser;
  protected final UserUtil userUtil;
  protected final SearchService searchService;
  protected final ChangeHistoryManager changeHistoryManager;

  protected final IUserWrapperDaoService userWrapperDaoService;
  protected final IUserStatisticDaoService userStatisticDaoService;
  protected final IProjectStatisticDaoService projectStatisticDaoService;

  public AbstractStatisticCalculator(SearchService searchService, UserUtil userUtil, JqlQueryParser jqlQueryParser, ChangeHistoryManager changeHistoryManager, IUserStatisticDaoService userStatisticDaoService, IUserWrapperDaoService userWrapperDaoService, IProjectStatisticDaoService projectStatisticDaoService)
  {
    this.searchService = searchService;
    this.userUtil = userUtil;
    this.jqlQueryParser = jqlQueryParser;
    this.changeHistoryManager = changeHistoryManager;
    this.userStatisticDaoService = userStatisticDaoService;
    this.userWrapperDaoService = userWrapperDaoService;
    this.projectStatisticDaoService = projectStatisticDaoService;
  }

  protected abstract StatisticRefEnum getStatisticRef();

  protected User retrieveAdministrator()
  {
    for (User user : userUtil.getJiraAdministrators())
    {
      return user;
    }

    return null;
  }

  protected void saveStatisticsByProject(Map<String, TObjectIntHashMap<String>> resolvedByUserByProject)
  {
    for (final Map.Entry<String, TObjectIntHashMap<String>> kvp : resolvedByUserByProject.entrySet())
    {
      kvp.getValue().forEachEntry(new TObjectIntProcedure<String>()
      {
        @Override
        public boolean execute(String userName, int count)
        {
          UserWrapper userWrapper = userWrapperDaoService.get(userName);
          if (userWrapper != null)
          {
            projectStatisticDaoService.createOrUpdate(userWrapper, kvp.getKey(), getStatisticRef(), count);
          }
          else
          {
            logger.warn("Couldn't retrieve userwrapper for username '{}'", userName);
          }

          return true;
        }
      });
    }
  }

  protected void saveStatisticsByUser(TObjectIntHashMap<String> resolvedByUser)
  {
    resolvedByUser.forEachEntry(new TObjectIntProcedure<String>()
    {
      @Override
      public boolean execute(String userName, int count)
      {
        UserWrapper userWrapper = userWrapperDaoService.get(userName);
        if (userWrapper != null)
        {
          userStatisticDaoService.createOrUpdate(getStatisticRef(), userWrapper, count);
        }
        else
        {
          logger.warn("Couldn't retrieve userwrapper for username '{}'", userName);
        }

        return false;
      }
    });
  }
}
