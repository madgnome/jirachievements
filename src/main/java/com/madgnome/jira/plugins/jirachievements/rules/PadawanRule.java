package com.madgnome.jira.plugins.jirachievements.rules;

import com.atlassian.jira.event.type.EventType;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.madgnome.jira.plugins.jirachievements.data.ao.*;
import com.madgnome.jira.plugins.jirachievements.data.services.IAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserStatisticDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;
import com.madgnome.jira.plugins.jirachievements.services.WorkflowConfiguration;

import java.util.HashMap;
import java.util.Map;

public class PadawanRule extends AbstractRule implements IRule
{
  private final IUserStatisticDaoService userStatisticDaoService;
  private final Map<Long, StatisticRefEnum> statisticByEventType;
  private final Map<StatisticRefEnum, AchievementRefEnum> achievementByStatistic;

  public PadawanRule(JiraAuthenticationContext jiraAuthenticationContext,
                     IUserWrapperDaoService userWrapperDaoService,
                     IAchievementDaoService achievementDaoService,
                     IUserAchievementDaoService userAchievementDaoService,
                     IUserStatisticDaoService userStatisticDaoService,
                     WorkflowConfiguration workflowConfiguration)
  {
    super(jiraAuthenticationContext, userWrapperDaoService, achievementDaoService, userAchievementDaoService, workflowConfiguration);
    this.userStatisticDaoService = userStatisticDaoService;

    statisticByEventType = createStatisticByEventType();
    achievementByStatistic = createAchievementByStatistic();
  }

  @Override
  public AchievementRefEnum getAchievementRef()
  {
    return null;
  }

  @Override
  public void innerCheck()
  {
    for (long issueEvenType :statisticByEventType.keySet())
    {
      check(issueEvenType);
    }
  }

  public void check(Long issueEventType)
  {
    StatisticRefEnum statisticRefEnum = statisticByEventType.get(issueEventType);
    if (statisticRefEnum != null)
    {
      for (UserWrapper userWrapper : userWrapperDaoService.all())
      {
        checkForUser(statisticRefEnum, userWrapper);
      }
    }
  }

  private void checkForUser(StatisticRefEnum statisticRefEnum, UserWrapper userWrapper)
  {
    UserStatistic userStatistic = userStatisticDaoService.get(userWrapper, statisticRefEnum);
    if (userStatistic.getValue() > 0)
    {
      Achievement achievement = achievementDaoService.getOrCreate(achievementByStatistic.get(statisticRefEnum));
      userAchievementDaoService.addAchievementToUser(achievement, userWrapper);
    }
  }

  private Map<Long, StatisticRefEnum> createStatisticByEventType()
  {
    Map<Long, StatisticRefEnum> statisticByEventType = new HashMap<Long, StatisticRefEnum>();
    statisticByEventType.put(EventType.ISSUE_CREATED_ID, StatisticRefEnum.CREATED_ISSUE_COUNT);
    statisticByEventType.put(EventType.ISSUE_RESOLVED_ID, StatisticRefEnum.RESOLVED_ISSUE_COUNT);
    statisticByEventType.put(EventType.ISSUE_CLOSED_ID, StatisticRefEnum.TESTED_ISSUE_COUNT);

    return statisticByEventType;
  }

   private Map<StatisticRefEnum, AchievementRefEnum> createAchievementByStatistic()
  {
    Map<StatisticRefEnum, AchievementRefEnum> achievementByStatistic = new HashMap<StatisticRefEnum, AchievementRefEnum>();
    achievementByStatistic.put(StatisticRefEnum.CREATED_ISSUE_COUNT, AchievementRefEnum.PADAWAN_USER);
    achievementByStatistic.put(StatisticRefEnum.RESOLVED_ISSUE_COUNT, AchievementRefEnum.PADAWAN_DEVELOPER);
    achievementByStatistic.put(StatisticRefEnum.TESTED_ISSUE_COUNT, AchievementRefEnum.PADAWAN_TESTER);

    return achievementByStatistic;
  }
}
