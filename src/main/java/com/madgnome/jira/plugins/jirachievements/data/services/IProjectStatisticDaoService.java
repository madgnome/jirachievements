package com.madgnome.jira.plugins.jirachievements.data.services;

import com.atlassian.activeobjects.tx.Transactional;
import com.madgnome.jira.plugins.jirachievements.data.ao.ProjectStatistic;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;

import java.util.List;

@Transactional
public interface IProjectStatisticDaoService extends IDaoService<ProjectStatistic>
{
  ProjectStatistic get(UserWrapper userWrapper, String projectKey, StatisticRefEnum statisticRefEnum);
  ProjectStatistic createOrUpdate(UserWrapper userWrapper, String projectKey, StatisticRefEnum statisticRefEnum, int value);

  List<ProjectStatistic> findStatisticsForProjectAndRef(String projectKey, StatisticRefEnum statisticRefEnum);
}
