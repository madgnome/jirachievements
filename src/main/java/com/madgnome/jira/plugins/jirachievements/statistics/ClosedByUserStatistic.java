package com.madgnome.jira.plugins.jirachievements.statistics;

import com.atlassian.jira.issue.changehistory.ChangeHistoryManager;
import com.atlassian.jira.user.util.UserUtil;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.services.StatisticManager;
import com.madgnome.jira.plugins.jirachievements.services.UserManager;
import com.madgnome.jira.plugins.jirachievements.services.WorkflowConfiguration;
import com.madgnome.jira.plugins.jirachievements.utils.data.IssueSearcher;

import java.util.List;

public class ClosedByUserStatistic extends OnFieldChangedValueStatistic
{

  public ClosedByUserStatistic(IssueSearcher issueSearcher, UserUtil userUtil, ChangeHistoryManager changeHistoryManager, StatisticManager statisticManager, UserManager userManager, WorkflowConfiguration workflowConfiguration)
  {
    super(issueSearcher, userUtil, changeHistoryManager, statisticManager, userManager, workflowConfiguration);
  }

  @Override
  protected List<String> getFieldValues()
  {
    return workflowConfiguration.getStatuses(WorkflowConfiguration.NormalizedStatus.RESOLVED);
  }

  @Override
  protected String getFieldName()
  {
    return "status";
  }

  @Override
  protected String getJQLQuery()
  {
    return "status IN (" + workflowConfiguration.getStatusesAsCSV(WorkflowConfiguration.NormalizedStatus.CLOSED) + ")";
  }

  @Override
  protected StatisticRefEnum getStatisticRef()
  {
    return StatisticRefEnum.TESTED_ISSUE_COUNT;
  }
}
