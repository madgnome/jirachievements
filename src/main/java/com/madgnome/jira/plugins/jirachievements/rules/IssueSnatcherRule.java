package com.madgnome.jira.plugins.jirachievements.rules;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.changehistory.ChangeHistoryItem;
import com.atlassian.jira.issue.changehistory.ChangeHistoryManager;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.AchievementRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.IAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;
import com.madgnome.jira.plugins.jirachievements.services.WorkflowConfiguration;
import com.madgnome.jira.plugins.jirachievements.utils.data.IssueSearcher;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

public class IssueSnatcherRule extends AbstractRule implements IRule
{
  private final IssueSearcher issueSearcher;
  private final ChangeHistoryManager changeHistoryManager;

  public IssueSnatcherRule(JiraAuthenticationContext jiraAuthenticationContext,
                           IUserWrapperDaoService userWrapperDaoService,
                           IAchievementDaoService achievementDaoService,
                           IUserAchievementDaoService userAchievementDaoService,
                           IssueSearcher issueSearcher,
                           ChangeHistoryManager changeHistoryManager,
                           WorkflowConfiguration workflowConfiguration)
  {
    super(jiraAuthenticationContext, userWrapperDaoService, achievementDaoService, userAchievementDaoService, workflowConfiguration);
    this.issueSearcher = issueSearcher;
    this.changeHistoryManager = changeHistoryManager;
  }

  @Override
  public AchievementRefEnum getAchievementRef()
  {
    return AchievementRefEnum.ISSUE_SNATCHER;
  }

  @Override
  public void check()
  {
    List<Issue> issues = issueSearcher.searchIssues("status WAS IN (" + workflowConfiguration.getStatusesAsCSV(WorkflowConfiguration.NormalizedStatus.REOPENED) + ")");

    Calendar calendar = Calendar.getInstance();
    for (Issue issue : issues)
    {
      // ReopenedDate + 1 year < CloseDate
      calendar.setTime(issue.getCreated());
      calendar.add(Calendar.YEAR, 1);

      Timestamp resolutionDate = issue.getResolutionDate();
      if (resolutionDate != null && resolutionDate.after(calendar.getTime()))
      {
        UserWrapper userWrapper = getResolver(issue);
        if (userWrapper != null)
        {
          Achievement achievement = achievementDaoService.get(getAchievementRef());
          if (userAchievementDaoService.get(achievement, userWrapper) == null)
          {
            userAchievementDaoService.addAchievementToUser(achievement, userWrapper);
          }
        }
      }
    }
  }

  private UserWrapper getResolver(Issue issue)
  {
    List<ChangeHistoryItem> changeHistoryItems = changeHistoryManager.getAllChangeItems(issue);
    for (ChangeHistoryItem changeHistoryItem : changeHistoryItems)
    {
      if (changeHistoryItem.getField().equals("status") &&
          changeHistoryItem.getTo().equals("Resolved"))
      {
        return userWrapperDaoService.get(changeHistoryItem.getUser());
      }
    }

    return null;
  }
}
