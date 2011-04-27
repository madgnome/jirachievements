package com.madgnome.jira.plugins.jirachievements;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.extension.Startable;
import com.atlassian.jira.user.util.UserUtil;
import com.madgnome.jira.plugins.jirachievements.data.services.IAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IStatisticRefDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;
import com.madgnome.jira.plugins.jirachievements.rules.WelcomeRule;
import com.madgnome.jira.plugins.jirachievements.utils.initializers.AchievementsInitializer;
import com.madgnome.jira.plugins.jirachievements.utils.initializers.StatisticsInitializer;

public class PluginInitializer implements Startable
{
  // TODO Replace by an ordered list of ITableInitializer
  private final AchievementsInitializer achievementsInitializer;
  private final StatisticsInitializer statisticsInitializer;
  private final UserUtil userUtil;
  private final IUserWrapperDaoService userWrapperDaoService;
  private final IStatisticRefDaoService statisticRefDaoService;

  private final WelcomeRule welcomeRule;

  public PluginInitializer(IUserWrapperDaoService userWrapperDaoService, UserUtil userUtil, IAchievementDaoService achievementDaoService, IStatisticRefDaoService statisticRefDaoService, AchievementsInitializer achievementsInitializer, WelcomeRule welcomeRule, StatisticsInitializer statisticsInitializer)
  {
    this.userWrapperDaoService = userWrapperDaoService;
    this.userUtil = userUtil;
    this.statisticRefDaoService = statisticRefDaoService;
    this.achievementsInitializer = achievementsInitializer;
    this.welcomeRule = welcomeRule;
    this.statisticsInitializer = statisticsInitializer;
  }

  @Override
  public void start() throws Exception
  {
    initDatabase();
  }

  private void initDatabase()
  {
    statisticsInitializer.initialize();
    achievementsInitializer.initialize();
    initStatistics();
    initUserWrappers();
  }

  private void initStatistics()
  {
    statisticRefDaoService.getOrCreate("IssueCount");
    statisticRefDaoService.getOrCreate("IssueCount");
  }

  private void initUserWrappers()
  {
    for (User user : userUtil.getUsers())
    {
      if (userWrapperDaoService.get(user) == null)
      {
        userWrapperDaoService.create(user);

        welcomeRule.execute(user);
      }
    }
  }
}
