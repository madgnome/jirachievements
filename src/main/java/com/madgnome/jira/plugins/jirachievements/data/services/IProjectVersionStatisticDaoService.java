package com.madgnome.jira.plugins.jirachievements.data.services;

import com.atlassian.activeobjects.tx.Transactional;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.ao.VersionStatistic;

import java.util.List;

@Transactional
public interface IProjectVersionStatisticDaoService extends IDaoService<VersionStatistic>
{
  VersionStatistic get(UserWrapper userWrapper, String projectKey, String version, StatisticRefEnum statisticRefEnum);
  VersionStatistic createOrUpdate(UserWrapper userWrapper, String projectKey, String version, StatisticRefEnum statisticRefEnum, int value);

  List<VersionStatistic> findStatisticsForVersionAndRef(String projectKey, String version, StatisticRefEnum statisticRefEnum);
}
