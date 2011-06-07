package com.madgnome.jira.plugins.jirachievements.statistics;

import com.atlassian.jira.issue.changehistory.ChangeHistoryManager;
import com.atlassian.jira.user.util.UserUtil;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.services.StatisticManager;
import com.madgnome.jira.plugins.jirachievements.services.UserManager;
import com.madgnome.jira.plugins.jirachievements.utils.data.IssueSearcher;

public class ResolvedByUserStatistic extends OnFieldChangedValueStatistic
{
  public ResolvedByUserStatistic(IssueSearcher issueSearcher, UserUtil userUtil, ChangeHistoryManager changeHistoryManager, StatisticManager statisticManager, UserManager userManager)
  {
    super(issueSearcher, userUtil, changeHistoryManager, statisticManager, userManager);
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
