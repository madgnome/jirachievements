package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;

public class BaseDaoService
{
  protected final ActiveObjects ao;

  public BaseDaoService(ActiveObjects ao)
  {
    this.ao = ao;
  }
}
