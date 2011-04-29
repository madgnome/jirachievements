package com.madgnome.jira.plugins.jirachievements.data.ao;

import net.java.ao.Entity;
import net.java.ao.Polymorphic;

@Polymorphic
public interface Statistic extends Entity
{
  StatisticRef getStatisticRef();
  void setStatisticRef(StatisticRef statisticRef);

  int getValue();
  void setValue(int value);
}
