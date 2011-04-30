package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRef;
import org.junit.Before;

public class StatisticRefDaoServiceTest extends ReferencableDaoServiceTest<StatisticRef, StatisticRefDaoService>
{
  @Before
  public void setUp() throws Exception
  {
    referencableDaoService = new StatisticRefDaoService(createActiveObjects());
  }
}
