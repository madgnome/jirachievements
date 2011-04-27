package com.madgnome.jira.plugins.jirachievements.data.ao;

import net.java.ao.Entity;

public interface ProjectStatistic extends Entity
{
  StatisticRef getStatisticRef();
  void setStatisticRef(StatisticRef statisticRef);

  String getValue();
  void setValue(String value);

  UserWrapper getUserWrapper();
  void setUserWrapper(UserWrapper userWrapper);

  String getProjectKey();
  void setProjectKey(String projectKey);
}
