package com.madgnome.jira.plugins.jirachievements.data.ao;

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
