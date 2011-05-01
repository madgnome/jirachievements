package com.madgnome.jira.plugins.jirachievements.data.services;

import com.madgnome.jira.plugins.jirachievements.data.ao.Category;
import com.madgnome.jira.plugins.jirachievements.data.ao.Level;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;

import java.util.List;

public interface ILevelDaoService extends IDaoService<Level>
{
  Level get(Category category, int number);
  Level getOrCreate(Category category, int number, StatisticRefEnum statisticRefEnum, int min, int max);
  List<Level> all(Category category);
  Level findMatchingLevel(Category category, int value);
}
