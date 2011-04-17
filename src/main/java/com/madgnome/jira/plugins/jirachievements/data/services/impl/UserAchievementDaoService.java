package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserAchievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserAchievementDaoService;

import java.util.List;

public class UserAchievementDaoService extends BaseDaoService<UserAchievement> implements IUserAchievementDaoService
{
  @Override
  protected Class<UserAchievement> getClazz()
  {
    return UserAchievement.class;
  }

  public UserAchievementDaoService(ActiveObjects ao)
  {
    super(ao);
  }

  @Override
  public void addAchievementToUser(Achievement achievement, UserWrapper userWrapper)
  {
    UserAchievement userAchievement = ao.create(UserAchievement.class);
    userAchievement.setUserWrapper(userWrapper);
    userAchievement.setAchievement(achievement);
    userAchievement.save();
  }

  @Override
  public List<Achievement> findUserAchievements(int jiraUserId)
  {
//    ao.find(UserAchievement.class, )
    return null;
  }
}
