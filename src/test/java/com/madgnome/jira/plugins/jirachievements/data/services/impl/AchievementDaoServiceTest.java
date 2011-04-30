package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import org.junit.Before;

public class AchievementDaoServiceTest extends ReferencableDaoServiceTest<Achievement, AchievementDaoService>
{
  @Before
  public void setUp() throws Exception
  {
    referencableDaoService = new AchievementDaoService(createActiveObjects());
  }

}
