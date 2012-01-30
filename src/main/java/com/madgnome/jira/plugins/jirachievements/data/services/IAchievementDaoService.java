package com.madgnome.jira.plugins.jirachievements.data.services;

import com.atlassian.activeobjects.tx.Transactional;
import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.AchievementRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;

import java.util.List;
import java.util.Map;

@Transactional
public interface IAchievementDaoService extends IReferencableDaoService<Achievement, AchievementRefEnum>
{
  List<Achievement> allActive();
  Map<String, List<Achievement>> allGroupByCategoryName();

  void activate(int id, boolean activate);

  List<Achievement> getUserNewAchievements(UserWrapper userWrapper);
}
