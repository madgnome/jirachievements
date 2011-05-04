package com.madgnome.jira.plugins.jirachievements.statistics;

import com.atlassian.jira.issue.changehistory.ChangeHistoryManager;
import com.atlassian.jira.user.util.UserUtil;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.services.IProjectStatisticDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserStatisticDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;
import com.madgnome.jira.plugins.jirachievements.utils.data.IssueSearcher;

public class ClosedByUserStatistic extends OnFieldChangedValueStatistic
{

  public ClosedByUserStatistic(IssueSearcher issueSearcher, UserUtil userUtil, ChangeHistoryManager changeHistoryManager, IUserWrapperDaoService userWrapperDaoService, IUserStatisticDaoService userStatisticDaoService, IProjectStatisticDaoService projectStatisticDaoService)
  {
    super(issueSearcher, userUtil, changeHistoryManager, userWrapperDaoService, userStatisticDaoService, projectStatisticDaoService);
  }

  @Override
  protected String getFieldValue()
  {
    return "Closed";
  }

  @Override
  protected String getFieldName()
  {
    return "status";
  }

  @Override
  protected String getJQLQuery()
  {
    return "status = Closed";
  }

  @Override
  protected StatisticRefEnum getStatisticRef()
  {
    return StatisticRefEnum.TESTED_ISSUE_COUNT;
  }
}
