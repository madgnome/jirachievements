package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.madgnome.jira.plugins.jirachievements.data.ao.ProjectStatistic;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRef;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.IProjectStatisticDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IStatisticRefDaoService;
import net.java.ao.Query;

import java.util.Arrays;
import java.util.List;

public class ProjectStatisticDaoService extends BaseDaoService<ProjectStatistic> implements IProjectStatisticDaoService
{
  private final IStatisticRefDaoService statisticRefDaoService;

  @Override
  protected Class<ProjectStatistic> getClazz()
  {
    return ProjectStatistic.class;
  }

  public ProjectStatisticDaoService(ActiveObjects ao, IStatisticRefDaoService statisticRefDaoService)
  {
    super(ao);
    this.statisticRefDaoService = statisticRefDaoService;
  }
  
  @Override
  public ProjectStatistic get(UserWrapper userWrapper, String projectKey, String statisticRef)
  {
    return null;
  }

  @Override
  public ProjectStatistic createOrUpdate(UserWrapper userWrapper, String projectKey, String statRef, int value)
  {
    StatisticRef statisticRef = statisticRefDaoService.get(statRef);

    ProjectStatistic projectStatistic = null;
    if (statisticRef != null)
    {
      projectStatistic = getOrCreate(projectKey, statisticRef, userWrapper);
      projectStatistic.setValue(value);
      projectStatistic.save();
    }

    return projectStatistic;
  }

  @Override
  public List<ProjectStatistic> findStatisticsForProjectAndRef(String projectKey, String statisticRef)
  {
    StatisticRef statRef = statisticRefDaoService.get(statisticRef);
    Query query = Query.select().where("PROJECT_KEY = ? AND STATISTIC_REF_ID = ?", projectKey, statRef.getID()).order("VALUE DESC");
    return Arrays.asList(ao.find(getClazz(), query));
  }

  private ProjectStatistic getOrCreate(String projectKey, StatisticRef statisticRef, UserWrapper userWrapper)
  {
    final ProjectStatistic[] projectStatistics = ao.find(getClazz(), "STATISTIC_REF_ID = ? AND USER_WRAPPER_ID = ? AND PROJECT_KEY = ?",
                                                                      statisticRef.getID(),
                                                                      userWrapper.getID(),
                                                                      projectKey);
    if (projectStatistics.length > 1)
    {
      throw new IllegalStateException("Found more than one statistic (" + projectStatistics.length + ") with ref " + statisticRef.getRef() + " for user " + userWrapper.getJiraUserName());
    }

    return projectStatistics.length == 0 ? create(projectKey, statisticRef, userWrapper) : projectStatistics[0];
  }

  private ProjectStatistic create(String projectKey, StatisticRef statisticRef, UserWrapper userWrapper)
  {
    ProjectStatistic projectStatistic = ao.create(getClazz());
    projectStatistic.setProjectKey(projectKey);
    projectStatistic.setStatisticRef(statisticRef);
    projectStatistic.setUserWrapper(userWrapper);
    projectStatistic.save();

    return projectStatistic;
  }


}
