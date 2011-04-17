package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRef;
import com.madgnome.jira.plugins.jirachievements.data.services.IStatisticRefDaoService;

public class StatisticRefDaoService extends ReferencableDaoService<StatisticRef> implements IStatisticRefDaoService
{
  @Override
  protected Class<StatisticRef> getClazz()
  {
    return StatisticRef.class;
  }

  public StatisticRefDaoService(ActiveObjects ao)
  {
    super(ao);
  }
}
