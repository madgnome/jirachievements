package com.madgnome.jira.plugins.jirachievements.rules;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.event.type.EventType;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.madgnome.jira.plugins.jirachievements.data.ao.*;
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
  public AchievementRefEnum getAchievementRef()
  {
    return AchievementRefEnum.PADAWAN_USER;
  }

  @Override
  public void execute(IssueEvent issueEvent)
  {
    Long eventTypeId = issueEvent.getEventTypeId();

    if (EventType.ISSUE_CREATED_ID.equals(eventTypeId))
    {
      User user = jiraAuthenticationContext.getLoggedInUser();
      UserWrapper userWrapper = userWrapperDaoService.get(user);
      UserStatistic userStatistic = userStatisticDaoService.get(userWrapper, StatisticRefEnum.CREATED_ISSUE_COUNT);
      if (userStatistic.getValue() == 1)
      {
        Achievement achievement = achievementDaoService.getOrCreate(getAchievementRef());
        userAchievementDaoService.addAchievementToUser(achievement, userWrapper);
      }
    }
  }


}
