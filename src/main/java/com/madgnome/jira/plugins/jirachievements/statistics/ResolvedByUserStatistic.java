package com.madgnome.jira.plugins.jirachievements.statistics;

import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.issue.changehistory.ChangeHistoryManager;
import com.atlassian.jira.jql.parser.JqlQueryParser;
import com.atlassian.jira.user.util.UserUtil;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.services.IProjectStatisticDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserStatisticDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;

public class ResolvedByUserStatistic extends OnFieldChangedValueStatistic
{
  public ResolvedByUserStatistic(SearchService searchService, UserUtil userUtil, JqlQueryParser jqlQueryParser, ChangeHistoryManager changeHistoryManager, IUserStatisticDaoService userStatisticDaoService, IUserWrapperDaoService userWrapperDaoService, IProjectStatisticDaoService projectStatisticDaoService)
  {
    super(searchService, userUtil, jqlQueryParser, changeHistoryManager, userStatisticDaoService, userWrapperDaoService, projectStatisticDaoService);
  }

  @Override
  protected String getFieldName()
  {
    return "status";
  }

  @Override
  protected String getFieldValue()
  {
    return "Resolved";
  }

  @Override
  protected String getJQLQuery()
  {
    return "status WAS Resolved";
  }

  @Override
  protected StatisticRefEnum getStatisticRef()
  {
    return StatisticRefEnum.RESOLVED_ISSUE_COUNT;
  }
}
