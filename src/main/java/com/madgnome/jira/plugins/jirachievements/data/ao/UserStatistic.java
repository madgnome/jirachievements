package com.madgnome.jira.plugins.jirachievements.data.ao;

import net.java.ao.Entity;

public interface UserStatistic extends Entity
{
  public int getUserWrapper();
  public void setUserWrapper(UserWrapper userWrapper);

  public StatisticRef getStatisticRef();
  public void setStatisticRef(StatisticRef statisticRef);

  public String getValue();
  public void setValue(String value);
}
