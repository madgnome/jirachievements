package com.madgnome.jira.plugins.jirachievements.data.ao;

import net.java.ao.Entity;
import net.java.ao.ManyToMany;

public interface UserWrapper extends Entity
{
  String getJiraUserName();
  void setJiraUserName(String name);

  @ManyToMany(value = UserAchievement.class, where = "NOTIFIED = FALSE")
  Achievement[] getNewAchievements();

  @ManyToMany(value = UserAchievement.class)
  Achievement[] getAchievements();

  @ManyToMany(UserStatistic.class)
  StatisticRef[] getStatistics();
}
