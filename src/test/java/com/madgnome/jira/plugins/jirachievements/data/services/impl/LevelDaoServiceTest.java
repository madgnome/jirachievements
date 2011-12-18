package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.madgnome.jira.plugins.jirachievements.data.ao.Category;
import com.madgnome.jira.plugins.jirachievements.data.ao.Level;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LevelDaoServiceTest extends BaseDaoServiceTest<Level, LevelDaoService>
{
  private StatisticRefDaoService statisticRefDaoService;

  @Before
  public void setUp()
  {
    ActiveObjects ao = createActiveObjects();
    statisticRefDaoService = new StatisticRefDaoService(ao);
    daoService = new LevelDaoService(ao, statisticRefDaoService);
  }

  @Test
  public void getShouldReturnNullIfLevelDoesntExist()
  {
    assertNull(daoService.get(Category.USER, 0));
  }

  @Test
  public void getOrCreateShouldCreateIfAny()
  {
    Category category = Category.USER;
    int number = 0;
    statisticRefDaoService.create(StatisticRefEnum.CREATED_ISSUE_COUNT);

    assertNull(daoService.get(category, number));
    Level level = daoService.getOrCreate(category, number, StatisticRefEnum.CREATED_ISSUE_COUNT, 0, 10);
    assertNotNull(level);
    entityManager.flushAll();

    assertNotNull(daoService.get(category, number));
  }

  @Test
  public void allShouldReturnEmptyListIfAny()
  {
    assertEquals(0, daoService.all(Category.USER).size());
  }

  @Test
  public void findMatchingLevelShouldReturnLevelForGivenValue()
  {
    Category category = Category.USER;
    statisticRefDaoService.create(StatisticRefEnum.CREATED_ISSUE_COUNT);
    daoService.getOrCreate(category, 0, StatisticRefEnum.CREATED_ISSUE_COUNT, 0, 10);
    daoService.getOrCreate(category, 1, StatisticRefEnum.CREATED_ISSUE_COUNT, 10, 50);
    daoService.getOrCreate(category, 2, StatisticRefEnum.CREATED_ISSUE_COUNT, 50, 150);
    daoService.getOrCreate(category, 3, StatisticRefEnum.CREATED_ISSUE_COUNT, 150, 500);
    daoService.getOrCreate(category, 4, StatisticRefEnum.CREATED_ISSUE_COUNT, 500, 2000);
    daoService.getOrCreate(category, 5, StatisticRefEnum.CREATED_ISSUE_COUNT, 2000, 5000);

    int value = 150;
    Level level = daoService.findMatchingLevel(category, value);
    assertNotNull(level);
    assertTrue(level.getMinThreshold() <= value);
    assertTrue(level.getMaxThreshold() > value);
  }

  @Test
  public void findNextLevelShouldReturnClosestNextLevelForGivenValue()
  {
    Category category = Category.USER;
    statisticRefDaoService.create(StatisticRefEnum.CREATED_ISSUE_COUNT);
    daoService.getOrCreate(category, 0, StatisticRefEnum.CREATED_ISSUE_COUNT, 0, 10);
    daoService.getOrCreate(category, 1, StatisticRefEnum.CREATED_ISSUE_COUNT, 10, 50);
    daoService.getOrCreate(category, 2, StatisticRefEnum.CREATED_ISSUE_COUNT, 50, 150);
    daoService.getOrCreate(category, 3, StatisticRefEnum.CREATED_ISSUE_COUNT, 150, 500);
    daoService.getOrCreate(category, 4, StatisticRefEnum.CREATED_ISSUE_COUNT, 500, 2000);
    daoService.getOrCreate(category, 5, StatisticRefEnum.CREATED_ISSUE_COUNT, 2000, 5000);

    int value = 150;
    Level currentLevel = daoService.findMatchingLevel(category, value);
    Level nextLevel = daoService.findNextLevel(category, value);
    assertNotNull(nextLevel);
    assertTrue(nextLevel.getMaxThreshold() > value);
    assertTrue(currentLevel.getLevelNumber() + 1 == nextLevel.getLevelNumber());
  }
}
