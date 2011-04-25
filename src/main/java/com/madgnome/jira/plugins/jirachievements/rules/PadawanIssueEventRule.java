package com.madgnome.jira.plugins.jirachievements.rules;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.event.type.EventType;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserStatistic;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.IAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserStatisticDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;

public class PadawanIssueEventRule extends AbstractRule implements IIssueEventRule
{
  private final IUserStatisticDaoService userStatisticDaoService;

  public PadawanIssueEventRule(JiraAuthenticationContext jiraAuthenticationContext, IUserWrapperDaoService userWrapperDaoService, IAchievementDaoService achievementDaoService, IUserAchievementDaoService userAchievementDaoService, IUserStatisticDaoService userStatisticDaoService)
  {
    super(jiraAuthenticationContext, userWrapperDaoService, achievementDaoService, userAchievementDaoService);
    this.userStatisticDaoService = userStatisticDaoService;
  }

  @Override
  public String getAchievementRef()
  {
    return "padawan:user";
  }

  @Override
  public void execute(IssueEvent issueEvent)
  {
    Long eventTypeId = issueEvent.getEventTypeId();

    if (EventType.ISSUE_CREATED_ID.equals(eventTypeId))
    {
      User user = jiraAuthenticationContext.getLoggedInUser();
      UserWrapper userWrapper = userWrapperDaoService.getUserWrapper(user);
      UserStatistic userStatistic = userStatisticDaoService.getStatistic(userWrapper, "IssueCount");
      if (!"0".equals(userStatistic.getValue()))
      {
        Achievement achievement = achievementDaoService.getOrCreate(getAchievementRef());
        userAchievementDaoService.addAchievementToUser(achievement, userWrapper);
      }
    }
  }


}
