package com.madgnome.jira.plugins.jirachievements.utils.initializers;

import com.madgnome.jira.plugins.jirachievements.data.services.IProjectVersionStatisticDaoService;

public class ProjectVersionStatisticInitializer extends ClearTableInitializer<IProjectVersionStatisticDaoService>
{
   public ProjectVersionStatisticInitializer(IProjectVersionStatisticDaoService daoService)
  {
    super(daoService);
  }
}
