package com.madgnome.jira.plugins.jirachievements.data.services;

import com.atlassian.activeobjects.tx.Transactional;
import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.Difficulty;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserAchievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;

import java.util.List;
import java.util.Map;

@Transactional
public interface IUserAchievementDaoService extends IDaoService<UserAchievement>
{
  void addAchievementToUser(Achievement achievement, UserWrapper userWrapper);

  UserAchievement get(Achievement achievement, UserWrapper userWrapper);
  UserAchievement get(int achievementId, int userWrapperId);

  Map<Difficulty, Integer> getAchievementsByLevel(UserWrapper userWrapper);

  List<UserAchievement> last(int maxResult);
}
