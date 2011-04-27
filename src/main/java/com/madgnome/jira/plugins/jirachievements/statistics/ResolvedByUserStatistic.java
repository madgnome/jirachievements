package com.madgnome.jira.plugins.jirachievements.statistics;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.changehistory.ChangeHistoryItem;
import com.atlassian.jira.issue.changehistory.ChangeHistoryManager;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.issue.search.SearchResults;
import com.atlassian.jira.jql.parser.JqlParseException;
import com.atlassian.jira.jql.parser.JqlQueryParser;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.user.util.UserUtil;
import com.atlassian.jira.web.bean.PagerFilter;
import com.atlassian.query.Query;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.IProjectStatisticDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserStatisticDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;
import gnu.trove.TObjectIntHashMap;
import gnu.trove.TObjectIntProcedure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResolvedByUserStatistic
{
  private final static Logger logger = LoggerFactory.getLogger(ResolvedByUserStatistic.class);

  private final JqlQueryParser jqlQueryParser;
  private final UserUtil userUtil;
  private final SearchService searchService;
  private final ChangeHistoryManager changeHistoryManager;

  private final IUserWrapperDaoService userWrapperDaoService;
  private final IUserStatisticDaoService userStatisticDaoService;
  private final IProjectStatisticDaoService projectStatisticDaoService;

  public ResolvedByUserStatistic(SearchService searchService, UserUtil userUtil, JqlQueryParser jqlQueryParser, ChangeHistoryManager changeHistoryManager, IUserStatisticDaoService userStatisticDaoService, IUserWrapperDaoService userWrapperDaoService, IProjectStatisticDaoService projectStatisticDaoService)
  {
    this.searchService = searchService;
    this.userUtil = userUtil;
    this.jqlQueryParser = jqlQueryParser;
    this.changeHistoryManager = changeHistoryManager;
    this.userStatisticDaoService = userStatisticDaoService;
    this.userWrapperDaoService = userWrapperDaoService;
    this.projectStatisticDaoService = projectStatisticDaoService;
  }

  public void calculate() throws SearchException, JqlParseException
  {
    Query query = jqlQueryParser.parseQuery("status WAS Resolved");

    TObjectIntHashMap<String> resolvedByUser = new TObjectIntHashMap<String>();
    Map<String, TObjectIntHashMap<String>> resolvedByUserByProject = new HashMap<String, TObjectIntHashMap<String>>();

    SearchResults searchResults = searchService.search(retrieveAdministrator(), query, PagerFilter.getUnlimitedFilter());
    for (Issue issue : searchResults.getIssues())
    {
      Project project = issue.getProjectObject();
      TObjectIntHashMap<String> resolvedByUserForProject = resolvedByUserByProject.get(project.getKey());
      if (resolvedByUserForProject == null)
      {
        resolvedByUserForProject = new TObjectIntHashMap<String>();
        resolvedByUserByProject.put(project.getKey(), resolvedByUserForProject);
      }

      List<ChangeHistoryItem> changeHistoryItems = changeHistoryManager.getAllChangeItems(issue);
      for (ChangeHistoryItem changeHistoryItem : changeHistoryItems)
      {
        if (changeHistoryItem.getField().equals("status") &&
            changeHistoryItem.getTo().equals("Resolved"))
        {
          String user = changeHistoryItem.getUser();
          resolvedByUser.adjustOrPutValue(user, 1, 1);
          resolvedByUserForProject.adjustOrPutValue(user, 1, 1);

          break;
        }
      }
    }

    saveStatisticsByUser(resolvedByUser);
    saveStatisticsByProject(resolvedByUserByProject);
  }

  private void saveStatisticsByProject(Map<String, TObjectIntHashMap<String>> resolvedByUserByProject)
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
            projectStatisticDaoService.createOrUpdate(userWrapper, kvp.getKey(), "ResolvedIssueCount", Integer.toString(count));
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

  private void saveStatisticsByUser(TObjectIntHashMap<String> resolvedByUser)
  {
    resolvedByUser.forEachEntry(new TObjectIntProcedure<String>()
    {
      @Override
      public boolean execute(String userName, int count)
      {
        UserWrapper userWrapper = userWrapperDaoService.get(userName);
        if (userWrapper != null)
        {
          userStatisticDaoService.createOrUpdate("ResolvedIssueCount", userWrapper, Integer.toString(count));
        }
        else
        {
          logger.warn("Couldn't retrieve userwrapper for username '{}'", userName);
        }

        return false;
      }
    });
  }

  private User retrieveAdministrator()
  {
    for (User user : userUtil.getJiraAdministrators())
    {
      return user;
    }

    return null;
  }
}
