package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserAchievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserAchievementDaoService;

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
  public UserAchievement get(Achievement achievement, UserWrapper userWrapper)
  {
    return get(achievement.getID(), userWrapper.getID());
  }

  @Override
  public UserAchievement get(int achievementId, int userWrapperId)
  {
    UserAchievement[] userAchievements =
            ao.find(clazz, "ACHIEVEMENT_ID = ? AND USER_WRAPPER_ID = ?", achievementId, userWrapperId);

    return userAchievements.length > 0 ? userAchievements[0] : null;
  }
}
