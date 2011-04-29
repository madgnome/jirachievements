package com.madgnome.jira.plugins.jirachievements.data.ao;

import net.java.ao.Entity;

public interface UserStatistic extends Entity
{
  StatisticRef getStatisticRef();
  void setStatisticRef(StatisticRef statisticRef);

  UserWrapper getUserWrapper();
  void setUserWrapper(UserWrapper userWrapper);

  int getValue();
  void setValue(int value);


}
