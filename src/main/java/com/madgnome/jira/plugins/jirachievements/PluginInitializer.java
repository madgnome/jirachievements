package com.madgnome.jira.plugins.jirachievements;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.extension.Startable;
import com.atlassian.jira.user.util.UserUtil;
import com.madgnome.jira.plugins.jirachievements.data.services.IAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IStatisticRefDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;
import com.madgnome.jira.plugins.jirachievements.utils.initializers.AchievementsInitializer;

public class PluginInitializer implements Startable
{
  private final AchievementsInitializer achievementsInitializer;
  private final UserUtil userUtil;
  private final IUserWrapperDaoService userWrapperDaoService;
  private final IStatisticRefDaoService statisticRefDaoService;

  public PluginInitializer(IUserWrapperDaoService userWrapperDaoService, UserUtil userUtil, IAchievementDaoService achievementDaoService, IStatisticRefDaoService statisticRefDaoService, AchievementsInitializer achievementsInitializer)
  {
    this.userWrapperDaoService = userWrapperDaoService;
    this.userUtil = userUtil;
    this.statisticRefDaoService = statisticRefDaoService;
    this.achievementsInitializer = achievementsInitializer;
  }

  @Override
  public void start() throws Exception
  {
    initDatabase();
  }

  private void initDatabase()
  {
    initUserWrappers();
    achievementsInitializer.initialize();
    initStatistics();
  }

  private void initStatistics()
  {
    statisticRefDaoService.getOrCreate("IssueCount");
  }

  private void initUserWrappers()
  {
    for (User user : userUtil.getUsers())
    {
      userWrapperDaoService.createUserWrapper(user);
    }
  }
}
