package com.madgnome.jira.plugins.jirachievements.utils.initializers;

import com.madgnome.jira.plugins.jirachievements.data.ao.Category;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.services.ILevelDaoService;

public class LevelsInitializer implements ITableInitializer
{
  private final ILevelDaoService levelDaoService;

  public LevelsInitializer(ILevelDaoService levelDaoService)
  {
    this.levelDaoService = levelDaoService;
  }

  @Override
  public void initialize()
  {
    initializeLevelsForUser();
    initializeLevelsForDeveloper();
    initializeLevelsForTester();
  }

  private void initializeLevelsForUser()
  {
    levelDaoService.getOrCreate(Category.USER, 0, StatisticRefEnum.CREATED_ISSUE_COUNT, 0, 5);
    levelDaoService.getOrCreate(Category.USER, 1, StatisticRefEnum.CREATED_ISSUE_COUNT, 5, 50);
    levelDaoService.getOrCreate(Category.USER, 2, StatisticRefEnum.CREATED_ISSUE_COUNT, 50, 200);
    levelDaoService.getOrCreate(Category.USER, 3, StatisticRefEnum.CREATED_ISSUE_COUNT, 200, 500);
    levelDaoService.getOrCreate(Category.USER, 4, StatisticRefEnum.CREATED_ISSUE_COUNT, 500, 1500);
    levelDaoService.getOrCreate(Category.USER, 5, StatisticRefEnum.CREATED_ISSUE_COUNT, 1500, 5000);
    levelDaoService.getOrCreate(Category.USER, 6, StatisticRefEnum.CREATED_ISSUE_COUNT, 5000, 10000);
    levelDaoService.getOrCreate(Category.USER, 7, StatisticRefEnum.CREATED_ISSUE_COUNT, 10000, 30000);
    levelDaoService.getOrCreate(Category.USER, 8, StatisticRefEnum.CREATED_ISSUE_COUNT, 30000, 50000);
    levelDaoService.getOrCreate(Category.USER, 9, StatisticRefEnum.CREATED_ISSUE_COUNT, 50000, 100000);
    levelDaoService.getOrCreate(Category.USER, 10, StatisticRefEnum.CREATED_ISSUE_COUNT, 100000, Integer.MAX_VALUE);
  }

  private void initializeLevelsForDeveloper()
  {
    levelDaoService.getOrCreate(Category.DEVELOPER, 0, StatisticRefEnum.RESOLVED_ISSUE_COUNT, 0, 5);
    levelDaoService.getOrCreate(Category.DEVELOPER, 1, StatisticRefEnum.RESOLVED_ISSUE_COUNT, 5, 20);
    levelDaoService.getOrCreate(Category.DEVELOPER, 2, StatisticRefEnum.RESOLVED_ISSUE_COUNT, 20, 50);
    levelDaoService.getOrCreate(Category.DEVELOPER, 3, StatisticRefEnum.RESOLVED_ISSUE_COUNT, 50, 150);
    levelDaoService.getOrCreate(Category.DEVELOPER, 4, StatisticRefEnum.RESOLVED_ISSUE_COUNT, 150, 500);
    levelDaoService.getOrCreate(Category.DEVELOPER, 5, StatisticRefEnum.RESOLVED_ISSUE_COUNT, 500, 1000);
    levelDaoService.getOrCreate(Category.DEVELOPER, 6, StatisticRefEnum.RESOLVED_ISSUE_COUNT, 1000, 2000);
    levelDaoService.getOrCreate(Category.DEVELOPER, 7, StatisticRefEnum.RESOLVED_ISSUE_COUNT, 2000, 5000);
    levelDaoService.getOrCreate(Category.DEVELOPER, 8, StatisticRefEnum.RESOLVED_ISSUE_COUNT, 5000, 10000);
    levelDaoService.getOrCreate(Category.DEVELOPER, 9, StatisticRefEnum.RESOLVED_ISSUE_COUNT, 10000, 20000);
    levelDaoService.getOrCreate(Category.DEVELOPER, 10, StatisticRefEnum.RESOLVED_ISSUE_COUNT, 20000, Integer.MAX_VALUE);
  }

  private void initializeLevelsForTester()
  {
    levelDaoService.getOrCreate(Category.TESTER, 0, StatisticRefEnum.TESTED_ISSUE_COUNT, 0, 5);
    levelDaoService.getOrCreate(Category.TESTER, 1, StatisticRefEnum.TESTED_ISSUE_COUNT, 5, 50);
    levelDaoService.getOrCreate(Category.TESTER, 2, StatisticRefEnum.TESTED_ISSUE_COUNT, 50, 200);
    levelDaoService.getOrCreate(Category.TESTER, 3, StatisticRefEnum.TESTED_ISSUE_COUNT, 200, 500);
    levelDaoService.getOrCreate(Category.TESTER, 4, StatisticRefEnum.TESTED_ISSUE_COUNT, 500, 1500);
    levelDaoService.getOrCreate(Category.TESTER, 5, StatisticRefEnum.TESTED_ISSUE_COUNT, 1500, 5000);
    levelDaoService.getOrCreate(Category.TESTER, 6, StatisticRefEnum.TESTED_ISSUE_COUNT, 5000, 10000);
    levelDaoService.getOrCreate(Category.TESTER, 7, StatisticRefEnum.TESTED_ISSUE_COUNT, 10000, 30000);
    levelDaoService.getOrCreate(Category.TESTER, 8, StatisticRefEnum.TESTED_ISSUE_COUNT, 30000, 50000);
    levelDaoService.getOrCreate(Category.TESTER, 9, StatisticRefEnum.TESTED_ISSUE_COUNT, 50000, 100000);
    levelDaoService.getOrCreate(Category.TESTER, 10, StatisticRefEnum.TESTED_ISSUE_COUNT, 100000, Integer.MAX_VALUE);
  }
}
