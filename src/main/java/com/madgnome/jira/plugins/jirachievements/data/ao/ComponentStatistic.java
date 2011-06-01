package com.madgnome.jira.plugins.jirachievements.data.ao;

import net.java.ao.Entity;

public interface ComponentStatistic extends Entity
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
