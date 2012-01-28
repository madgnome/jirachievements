package com.madgnome.jira.plugins.jirachievements.services;

import com.madgnome.jira.plugins.jirachievements.data.ao.*;
import com.madgnome.jira.plugins.jirachievements.data.services.IProjectComponentStatisticDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IProjectStatisticDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IProjectVersionStatisticDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserStatisticDaoService;

public class StatisticManager
{
  private final IUserStatisticDaoService userStatisticDaoService;
  private final IProjectStatisticDaoService projectStatisticDaoService;
  private final IProjectComponentStatisticDaoService projectComponentStatisticDaoService;
  private final IProjectVersionStatisticDaoService projectVersionStatisticDaoService;

  public StatisticManager(IUserStatisticDaoService userStatisticDaoService, IProjectStatisticDaoService projectStatisticDaoService, IProjectComponentStatisticDaoService projectComponentStatisticDaoService, IProjectVersionStatisticDaoService projectVersionStatisticDaoService)
  {
    this.userStatisticDaoService = userStatisticDaoService;
    this.projectStatisticDaoService = projectStatisticDaoService;
    this.projectComponentStatisticDaoService = projectComponentStatisticDaoService;
    this.projectVersionStatisticDaoService = projectVersionStatisticDaoService;
  }

  public UserStatistic incrementUserStatistic(StatisticRefEnum statRef, UserWrapper userWrapper, int threshold)
  {
    return userStatisticDaoService.incrementStatistic(statRef, userWrapper, threshold);
  }

  public UserStatistic createOrUpdateUserStatistic(StatisticRefEnum statRef, UserWrapper userWrapper, int value)
  {
    return userStatisticDaoService.createOrUpdate(statRef, userWrapper, value);
  }

  public ProjectStatistic createOrUpdateProjectStatistic(UserWrapper userWrapper, String projectKey, StatisticRefEnum statisticRefEnum, int value)
  {
    return projectStatisticDaoService.createOrUpdate(userWrapper, projectKey, statisticRefEnum, value);
  }

  public ComponentStatistic createOrUpdateComponentStatistic(UserWrapper userWrapper, String projectKey, String component, StatisticRefEnum statisticRefEnum, int value)
  {
    return projectComponentStatisticDaoService.createOrUpdate(userWrapper, projectKey, component, statisticRefEnum, value);
  }

  public VersionStatistic createOrUpdateVersionStatistic(UserWrapper userWrapper, String projectKey, String version, StatisticRefEnum statisticRefEnum, int value)
  {
    return projectVersionStatisticDaoService.createOrUpdate(userWrapper, projectKey, version, statisticRefEnum, value);
  }
}
