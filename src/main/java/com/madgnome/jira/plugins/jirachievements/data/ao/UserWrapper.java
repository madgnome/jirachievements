package com.madgnome.jira.plugins.jirachievements.data.ao;

import net.java.ao.Entity;
import net.java.ao.ManyToMany;

public interface UserWrapper extends Entity
{
  public String getJiraUserName();
  public void setJiraUserName(String name);

  @ManyToMany(UserAchievement.class)
  public Achievement[] getAchievements();

  @ManyToMany(UserStatistic.class)
  public StatisticRef[] getStatistics();
}
