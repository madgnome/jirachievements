package com.madgnome.jira.plugins.jirachievements.data.services;

import com.atlassian.activeobjects.tx.Transactional;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserStatistic;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;

@Transactional
public interface IUserStatisticDaoService extends IDaoService<UserStatistic>
{
  UserStatistic get(UserWrapper userWrapper, StatisticRefEnum statisticRefEnum);
  UserStatistic createOrUpdate(StatisticRefEnum statRef, UserWrapper userWrapper, int value);
}
