package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserAchievements;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserAchievementsDaoService;

import java.util.List;

public class UserAchievementsDaoService extends BaseDaoService implements IUserAchievementsDaoService
{
  public UserAchievementsDaoService(ActiveObjects ao)
  {
    super(ao);
  }

  @Override
  public void addAchievementToUser(Achievement achievement, int jiraUserId)
  {
    UserAchievements userAchievements = ao.create(UserAchievements.class);
//    userAchievements.setUserId(jiraUserId);
    userAchievements.setAchievement(achievement);
    userAchievements.save();
  }

  @Override
  public List<Achievement> findUserAchievements(int jiraUserId)
  {
//    ao.find(UserAchievements.class, )
    return null;
  }
}
