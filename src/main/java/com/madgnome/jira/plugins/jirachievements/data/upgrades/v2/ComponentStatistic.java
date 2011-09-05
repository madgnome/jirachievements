package com.madgnome.jira.plugins.jirachievements.data.upgrades.v2;

import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRef;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;

public interface ComponentStatistic extends KeyableEntity
{
  StatisticRef getStatisticRef();
  void setStatisticRef(StatisticRef statisticRef);

  UserWrapper getUserWrapper();
  void setUserWrapper(UserWrapper userWrapper);

  String getProjectKey();
  void setProjectKey(String projectKey);

  int getValue();
  void setValue(int value);

  String getComponent();
  void setComponent(String component);
}
