package com.madgnome.jira.plugins.jirachievements.utils.initializers;

import com.madgnome.jira.plugins.jirachievements.data.services.IDaoService;

public class ClearTableInitializer<T extends IDaoService> implements ITableInitializer
{
  private final T daoService;

  public ClearTableInitializer(T daoService)
  {
    this.daoService = daoService;
  }

  @Override
  public void initialize()
  {
    daoService.deleteAll();
  }
}
