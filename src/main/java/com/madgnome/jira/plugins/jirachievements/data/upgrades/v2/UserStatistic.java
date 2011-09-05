package com.madgnome.jira.plugins.jirachievements.data.upgrades.v2;

import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRef;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;

public interface UserStatistic extends KeyableEntity
{
  StatisticRef getStatisticRef();
  void setStatisticRef(StatisticRef statisticRef);

  UserWrapper getUserWrapper();
  void setUserWrapper(UserWrapper userWrapper);

  int getValue();
  void setValue(int value);

  String getKey();
  void setKey(String key);
}
