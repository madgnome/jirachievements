package com.madgnome.jira.plugins.jirachievements.data.upgrades.v3;


import com.atlassian.activeobjects.external.IgnoreReservedKeyword;
import com.madgnome.jira.plugins.jirachievements.data.ao.Category;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRef;
import net.java.ao.Entity;
import net.java.ao.schema.Table;

@Table("Level")
public interface Level extends Entity
{
  int getLevelNumber();
  void setLevelNumber(int number);

  @IgnoreReservedKeyword
  int getNumber();
  @IgnoreReservedKeyword
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
