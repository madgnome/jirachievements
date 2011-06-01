package com.madgnome.jira.plugins.jirachievements.data.services;

import com.atlassian.activeobjects.tx.Transactional;
import com.madgnome.jira.plugins.jirachievements.data.ao.ComponentStatistic;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;

import java.util.List;

@Transactional
public interface IProjectComponentStatisticDaoService extends IDaoService<ComponentStatistic>
{
  ComponentStatistic get(UserWrapper userWrapper, String projectKey, String component, StatisticRefEnum statisticRefEnum);
  ComponentStatistic createOrUpdate(UserWrapper userWrapper, String projectKey, String component, StatisticRefEnum statisticRefEnum, int value);

  List<ComponentStatistic> findStatisticsForComponentAndRef(String projectKey, String component, StatisticRefEnum statisticRefEnum);
}
