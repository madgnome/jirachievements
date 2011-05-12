package com.madgnome.jira.plugins.jirachievements.data.ao;

import net.java.ao.Entity;
import net.java.ao.ManyToMany;
import net.java.ao.schema.Default;
import net.java.ao.schema.NotNull;
import net.java.ao.schema.Unique;

public interface UserWrapper extends Entity
{
  @Unique
  @NotNull
  String getJiraUserName();
  void setJiraUserName(String name);

  @ManyToMany(value = UserAchievement.class, where = "NOTIFIED = FALSE")
  Achievement[] getNewAchievements();

  @ManyToMany(value = UserAchievement.class)
  Achievement[] getAchievements();

  @ManyToMany(UserStatistic.class)
  StatisticRef[] getStatistics();

  @Default("true")
  boolean isActive();
  void setActive(boolean active);
}
