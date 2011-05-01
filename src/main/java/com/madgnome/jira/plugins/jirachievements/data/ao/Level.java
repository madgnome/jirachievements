package com.madgnome.jira.plugins.jirachievements.data.ao;


import net.java.ao.Entity;

public interface Level extends Entity
{
  int getNumber();
  void setNumber(int number);

  Category getCategory();
  void setCategory(Category category);

  StatisticRef getStatisticRef();
  void setStatisticRef(StatisticRef statisticRef);

  int getMinThreshold();
  void setMinThreshold(int min);

  int getMaxThreshold();
  void setMaxThreshold(int max);
}
