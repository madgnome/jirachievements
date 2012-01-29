package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.madgnome.jira.plugins.jirachievements.data.ao.*;
import com.madgnome.jira.plugins.jirachievements.data.services.IAchievementDaoService;
import net.java.ao.Query;

import java.util.*;

public class AchievementDaoService extends ReferencableDaoService<Achievement, AchievementRefEnum> implements IAchievementDaoService
{
  @Override
  protected Class<Achievement> getClazz()
  {
    return Achievement.class;
  }

  public AchievementDaoService(ActiveObjects ao)
  {
    super(ao);
  }

  public List<Achievement> allActive()
  {
    return Arrays.asList(ao.find(getClazz(), "ACTIVE = ?", true));
  }

  public Map<Category, List<Achievement>> allGroupByCategory()
  {
    Map<Category, List<Achievement>> achievementByCategory = new HashMap<Category, List<Achievement>>();
    List<Achievement> achievements = all();
    for (Achievement achievement : achievements)
    {
      Category category = achievement.getCategory();
      List<Achievement> categoryAchievements = achievementByCategory.get(category);
      if (categoryAchievements == null)
      {
        categoryAchievements = new ArrayList<Achievement>();
        achievementByCategory.put(category, categoryAchievements);
      }

      categoryAchievements.add(achievement);
    }
    
    return achievementByCategory;
  }

  public Map<String, List<Achievement>> allGroupByCategoryName()
  {
    Map<String, List<Achievement>> achievementByCategory = new HashMap<String, List<Achievement>>();
    List<Achievement> achievements = all();
    for (Achievement achievement : achievements)
    {
      Category category = achievement.getCategory();
      List<Achievement> categoryAchievements = achievementByCategory.get(category.toString());
      if (categoryAchievements == null)
      {
        categoryAchievements = new ArrayList<Achievement>();
        achievementByCategory.put(category.toString(), categoryAchievements);
      }

      categoryAchievements.add(achievement);
    }

    return achievementByCategory;
  }

  public void activate(int id, boolean activate)
  {
    Achievement achievement = get(id);
    if (achievement == null)
    {
      logger.warn("No achievement found with this id: ", id);
      return;
    }

    achievement.setActive(activate);
    achievement.save();
  }

  public List<Achievement> getUserNewAchievements(UserWrapper userWrapper)
  {
    Query query = Query.select()
                       .alias(UserAchievement.class, "ua")
                       .alias(Achievement.class, "a")
                       .join(UserAchievement.class,"ua.ACHIEVEMENT_ID = a.ID")
                       .where("USER_WRAPPER_ID = ? AND NOTIFIED = ?", userWrapper.getID(), false);

    return Arrays.asList(ao.find(Achievement.class, query));
  }
}
