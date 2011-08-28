package com.madgnome.jira.plugins.jirachievements.data.ao;

public interface UserStatistic extends KeyableEntity
{
  StatisticRef getStatisticRef();
  void setStatisticRef(StatisticRef statisticRef);

  UserWrapper getUserWrapper();
  void setUserWrapper(UserWrapper userWrapper);

  int getValue();
  void setValue(int value);


}
