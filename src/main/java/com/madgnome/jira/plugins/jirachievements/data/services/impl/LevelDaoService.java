package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.madgnome.jira.plugins.jirachievements.data.ao.Category;
import com.madgnome.jira.plugins.jirachievements.data.ao.Level;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRef;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.services.ILevelDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IStatisticRefDaoService;

import java.util.Arrays;
import java.util.List;

public class LevelDaoService extends BaseDaoService<Level> implements ILevelDaoService
{
  private final IStatisticRefDaoService statisticRefDaoService;

  @Override
  protected Class<Level> getClazz()
  {
    return Level.class;
  }

  public LevelDaoService(ActiveObjects ao, IStatisticRefDaoService statisticRefDaoService)
  {
    super(ao);
    this.statisticRefDaoService = statisticRefDaoService;
  }

  @Override
  public Level get(Category category, int number)
  {
    Level[] levels = ao.find(getClazz(), "CATEGORY = ? AND NUMBER = ?", category, number);
    if (levels.length > 1)
    {
      throw new IllegalStateException("Found more than one level of <" + category.name() + "-" + number + ">");
    }

    return levels.length > 0 ? levels[0] : null;
  }

  @Override
  public Level getOrCreate(Category category, int number, StatisticRefEnum statisticRefEnum, int min, int max)
  {
    Level level = get(category, number);

    return level == null ? create(category, number, statisticRefEnum, min, max) : level;
  }

  @Override
  public List<Level> all(Category category)
  {
    return Arrays.asList(ao.find(getClazz(), "CATEGORY = ?", category));
  }

  @Override
  public Level findMatchingLevel(Category category, int value)
  {
    Level[] levels = ao.find(getClazz(), "CATEGORY = ? AND MIN_THRESHOLD <= ? AND MAX_THRESHOLD > ?", category, value, value);

    return levels.length > 0 ? levels[0] : null;
  }

  @Override
  public Level findNextLevel(Category category, int value)
  {
    Level[] levels = ao.find(getClazz(), "CATEGORY = ? AND MIN_THRESHOLD > ? ORDER BY MIN_THRESHOLD ASC", category, value);

    return levels.length > 0 ? levels[0] : null;
  }

  private Level create(Category category, int number, StatisticRefEnum statisticRefEnum, int min, int max)
  {
    StatisticRef statisticRef = statisticRefDaoService.get(statisticRefEnum);
    if (statisticRef != null)
    {
      return create(category, number, statisticRef, min, max);
    }

    return null;
  }

  private Level create(Category category, int number, StatisticRef statisticRef, int min, int max)
  {
    Level level = ao.create(getClazz());
    level.setCategory(category);
    level.setNumber(number);
    level.setStatisticRef(statisticRef);
    level.setMinThreshold(min);
    level.setMaxThreshold(max);
    level.save();

    return level;
  }
}
