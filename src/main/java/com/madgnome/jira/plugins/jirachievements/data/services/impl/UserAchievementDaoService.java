package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.Difficulty;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserAchievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.utils.data.AOUtil;
import net.java.ao.Query;

import java.util.HashMap;
import java.util.Map;

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
    if (get(achievement, userWrapper) == null)
    {
      UserAchievement userAchievement = ao.create(UserAchievement.class);
      userAchievement.setUserWrapper(userWrapper);
      userAchievement.setAchievement(achievement);
      userAchievement.save();
    }
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

  @Override
  public Map<Difficulty, Integer> getAchievementsByLevel(UserWrapper userWrapper)
  {
    // TODO : Replace to do it in one request. Not possible for now with AO
    Map<Difficulty, Integer> achievementsByLevel = new HashMap<Difficulty, Integer>(Difficulty.values().length);
    for (Difficulty difficulty : Difficulty.values())
    {
      Query query = Query.select().join(Achievement.class, AOUtil.getTablePrefix() + "_ACHIEVEMENT.ID = ACHIEVEMENT_ID")
                                  .where("USER_WRAPPER_ID = ? AND DIFFICULTY = ? AND ACTIVE = ?", userWrapper.getID(), difficulty.ordinal(), true);
      achievementsByLevel.put(difficulty, ao.count(UserAchievement.class, query));
    }

    return achievementsByLevel;
  }
}
