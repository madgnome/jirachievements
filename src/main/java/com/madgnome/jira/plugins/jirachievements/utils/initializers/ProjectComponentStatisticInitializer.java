package com.madgnome.jira.plugins.jirachievements.utils.initializers;

import com.madgnome.jira.plugins.jirachievements.data.services.IProjectComponentStatisticDaoService;

public class ProjectComponentStatisticInitializer extends ClearTableInitializer<IProjectComponentStatisticDaoService>
{
  public ProjectComponentStatisticInitializer(IProjectComponentStatisticDaoService daoService)
  {
    super(daoService);
  }
}
