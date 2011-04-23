package com.madgnome.jira.plugins.jirachievements.data.ao;

import net.java.ao.OneToMany;

public interface StatisticRef extends ReferencableEntity
{
  @OneToMany
  public UserStatistic[] getUserStatistics();
}
