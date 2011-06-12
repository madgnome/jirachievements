package com.madgnome.jira.plugins.jirachievements.services;

import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.AchievementRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserAchievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.IAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserAchievementDaoService;

import java.util.List;

public class AchievementManager
{
  private final IAchievementDaoService achievementDaoService;
  private final IUserAchievementDaoService userAchievementDaoService;

  public AchievementManager(IAchievementDaoService achievementDaoService, IUserAchievementDaoService userAchievementDaoService)
  {
    this.achievementDaoService = achievementDaoService;
    this.userAchievementDaoService = userAchievementDaoService;
  }

  public Achievement addAchievementToUser(AchievementRefEnum achievementRefEnum, UserWrapper userWrapper)
  {
    Achievement achievement = achievementDaoService.get(achievementRefEnum);
    if (achievement != null)
    {
      userAchievementDaoService.addAchievementToUser(achievement, userWrapper);
    }

    return achievement;
  }

  public List<Achievement> allAchievements()
  {
    return achievementDaoService.all();
  }

  public UserAchievement updateNotification(int achievementId, UserWrapper userWrapper, boolean notified)
  {
    UserAchievement userAchievement = userAchievementDaoService.get(achievementId, userWrapper.getID());
    userAchievement.setNotified(notified);
    userAchievement.save();

    return userAchievement;
  }

  public void activate(int achievementId, boolean active)
  {
    achievementDaoService.activate(achievementId, active);
  }

  public List<Achievement> getUserNewAchievements(UserWrapper userWrapper)
  {
    return achievementDaoService.getUserNewAchievements(userWrapper);
  }
}
