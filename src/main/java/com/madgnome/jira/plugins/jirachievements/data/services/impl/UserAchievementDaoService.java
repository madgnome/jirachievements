package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.Difficulty;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserAchievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserAchievementDaoService;
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
    UserAchievement userAchievement = ao.create(UserAchievement.class);
    userAchievement.setUserWrapper(userWrapper);
    userAchievement.setAchievement(achievement);
//    userAchievement.setCreatedOn(Calendar.getInstance().getTime());
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

  @Override
  public Map<Difficulty, Integer> getAchievementsByLevel(UserWrapper userWrapper)
  {
//    Query query = Query.select("COUNT(*),ACHIEVEMENT_LEVEL").join(Achievement.class, "AO_7A05D7_ACHIEVEMENT.ID = ACHIEVEMENT_ID").where("USER_WRAPPER_ID = ?", userWrapper.getID()).group("ACHIEVEMENT_LEVEL");
//    Query query = Query.select().join(Achievement.class, "AO_7A05D7_ACHIEVEMENT.ID = ACHIEVEMENT_ID").where("USER_WRAPPER_ID = ?", userWrapper.getID());

    // TODO : Replace to do it in one request.
    Map<Difficulty, Integer> achievementsByLevel = new HashMap<Difficulty, Integer>(Difficulty.values().length);
    for (Difficulty difficulty : Difficulty.values())
    {
      Query query = Query.select().join(Achievement.class, "AO_7A05D7_ACHIEVEMENT.ID = ACHIEVEMENT_ID")
                                  .where("USER_WRAPPER_ID = ? AND DIFFICULTY = ?", userWrapper.getID(), difficulty.ordinal());
      achievementsByLevel.put(difficulty, ao.count(UserAchievement.class, query));
    }

    return achievementsByLevel;
  }
}
