package com.madgnome.jira.plugins.jirachievements.data.ao;

import net.java.ao.Entity;
import net.java.ao.OneToMany;

public interface StatisticRef extends Entity, ReferencableEntity
{
  @OneToMany
  public UserStatistic[] getUserStatistics();
}
