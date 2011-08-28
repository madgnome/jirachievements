package com.madgnome.jira.plugins.jirachievements.utils.initializers;

import com.madgnome.jira.plugins.jirachievements.data.services.IUserStatisticDaoService;

public class UserStatisticInitializer extends ClearTableInitializer<IUserStatisticDaoService>
{
  public UserStatisticInitializer(IUserStatisticDaoService daoService)
  {
    super(daoService);
  }
}
