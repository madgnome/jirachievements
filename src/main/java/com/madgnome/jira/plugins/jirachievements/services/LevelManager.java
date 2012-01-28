package com.madgnome.jira.plugins.jirachievements.services;

import com.madgnome.jira.plugins.jirachievements.data.ao.*;
import com.madgnome.jira.plugins.jirachievements.data.services.ILevelDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserLevelDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserStatisticDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;

public class LevelManager
{
  private final IUserWrapperDaoService userWrapperDaoService;
  private final IUserStatisticDaoService userStatisticDaoService;
  private final ILevelDaoService levelDaoService;
  private final IUserLevelDaoService userLevelDaoService;

  public LevelManager(IUserStatisticDaoService userStatisticDaoService, IUserWrapperDaoService userWrapperDaoService, IUserLevelDaoService userLevelDaoService, ILevelDaoService levelDaoService)
  {
    this.userStatisticDaoService = userStatisticDaoService;
    this.userWrapperDaoService = userWrapperDaoService;
    this.userLevelDaoService = userLevelDaoService;
    this.levelDaoService = levelDaoService;
  }

  public void checkLevelsForAllUser()
  {
    for (UserWrapper userWrapper : userWrapperDaoService.all())
    {
      checkLevels(userWrapper);
    }
  }
  
  public void checkLevels(UserWrapper userWrapper)
  {
    checkLevelForCategory(userWrapper, Category.USER, StatisticRefEnum.CREATED_ISSUE_COUNT);
    checkLevelForCategory(userWrapper, Category.DEVELOPER, StatisticRefEnum.RESOLVED_ISSUE_COUNT);
    checkLevelForCategory(userWrapper, Category.TESTER, StatisticRefEnum.TESTED_ISSUE_COUNT);
  }

  private void checkLevelForCategory(UserWrapper userWrapper, Category category, StatisticRefEnum statisticRefEnum)
  {
    // TODO; A link between statistic and category is missing I think
    UserStatistic createdIssueCountStatistic = userStatisticDaoService.get(userWrapper, statisticRefEnum);
    Level userLevel = levelDaoService.findMatchingLevel(category, createdIssueCountStatistic.getValue());
    Level lastUserLevel = userLevelDaoService.last(userWrapper, category);
    if (lastUserLevel != userLevel)
    {
      userLevelDaoService.addLevelToUser(userLevel, userWrapper);
    }
  }
}
