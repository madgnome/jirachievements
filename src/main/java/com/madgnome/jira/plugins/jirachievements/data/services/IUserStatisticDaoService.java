package com.madgnome.jira.plugins.jirachievements.data.services;

import com.atlassian.activeobjects.tx.Transactional;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserStatistic;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;

@Transactional
public interface IUserStatisticDaoService
{
  UserStatistic getStatistic(UserWrapper userWrapper, String ref);
}
