package com.madgnome.jira.plugins.jirachievements;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.extension.Startable;
import com.atlassian.jira.user.util.UserUtil;
import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.services.IAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IStatisticRefDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;

public class PluginInitializer implements Startable
{
  private final UserUtil userUtil;
  private final IUserWrapperDaoService userWrapperDaoService;
  private final IAchievementDaoService achievementDaoService;
  private final IStatisticRefDaoService statisticRefDaoService;

  public PluginInitializer(IUserWrapperDaoService userWrapperDaoService, UserUtil userUtil, IAchievementDaoService achievementDaoService, IStatisticRefDaoService statisticRefDaoService)
  {
    this.userWrapperDaoService = userWrapperDaoService;
    this.userUtil = userUtil;
    this.achievementDaoService = achievementDaoService;
    this.statisticRefDaoService = statisticRefDaoService;
  }

  @Override
  public void start() throws Exception
  {
    initDatabase();
  }

  private void initDatabase()
  {
    initUserWrappers();
    initAchievements();
    initStatistics();
  }

  private void initStatistics()
  {
    statisticRefDaoService.getOrCreate("IssueCount");
  }

  private void initAchievements()
  {
    achievementDaoService.getOrCreate("Padawan");
  }

  private void initUserWrappers()
  {
    for (User user : userUtil.getUsers())
    {
      userWrapperDaoService.createUserWrapper(user);
    }
  }

  private void initAchievement(String ref, String name, String description)
  {
    Achievement achievement = achievementDaoService.getOrCreate(ref);
    achievement.setName(name);
    achievement.setDescription(description);
    achievement.save();
  }
}
