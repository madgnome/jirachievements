package com.madgnome.jira.plugins.jirachievements.utils.initializers;

import com.madgnome.jira.plugins.jirachievements.data.services.IProjectStatisticDaoService;

public class ProjectStatisticInitializer extends ClearTableInitializer<IProjectStatisticDaoService>
{
  public ProjectStatisticInitializer(IProjectStatisticDaoService daoService)
  {
    super(daoService);
  }
}
