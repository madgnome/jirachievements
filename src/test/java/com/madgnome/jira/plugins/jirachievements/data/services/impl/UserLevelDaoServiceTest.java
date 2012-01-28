package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.crowd.embedded.api.User;
import com.atlassian.crowd.embedded.impl.ImmutableUser;
import com.madgnome.jira.plugins.jirachievements.data.ao.*;
import com.madgnome.jira.plugins.jirachievements.data.services.ILevelDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IStatisticRefDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserLevelDaoServiceTest extends BaseDaoServiceTest<UserLevel, UserLevelDaoService>
{
  private IUserWrapperDaoService userWrapperDaoService;
  private ILevelDaoService levelDaoService;
  private IStatisticRefDaoService statisticRefDaoService;
  
  @Before
  public void setUp()
  {
    ActiveObjects ao = createActiveObjects();
    userWrapperDaoService = new UserWrapperDaoService(ao);
    statisticRefDaoService = new StatisticRefDaoService(ao);
    levelDaoService = new LevelDaoService(ao, statisticRefDaoService);
    
    daoService = new UserLevelDaoService(ao);
  }
  
  @Test
  public void addUserLevelShouldAddLevelToUser()
  {
    UserWrapper userWrapper = createUserWrapper();
    statisticRefDaoService.getOrCreate(StatisticRefEnum.CREATED_ISSUE_COUNT);
    Level level = levelDaoService.getOrCreate(Category.USER, 0, StatisticRefEnum.CREATED_ISSUE_COUNT, 0, 5);
    
    daoService.addLevelToUser(level, userWrapper);

    final Level[] levels = userWrapper.getLevels();
    Assert.assertNotNull(levels[0]);
    Assert.assertEquals(levels[0], level);
  }

  private UserWrapper createUserWrapper()
  {
    User user = new ImmutableUser(0, "bob", "Sponge Bob", null, true);
    return userWrapperDaoService.create(user);
  }
}
