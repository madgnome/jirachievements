package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.crowd.embedded.api.User;
import com.atlassian.crowd.embedded.impl.ImmutableUser;
import com.madgnome.jira.plugins.jirachievements.data.ao.*;
import com.madgnome.jira.plugins.jirachievements.data.services.ILevelDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IStatisticRefDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
    assertNotNull(levels[0]);
    assertEquals(levels[0], level);
  }

  @Test
  public void lastShouldReturnLastEarnedLevelForUser() throws Exception
  {
    final UserWrapper userWrapper = userWrapperDaoService.create("bob", null);
    statisticRefDaoService.getOrCreate(StatisticRefEnum.CREATED_ISSUE_COUNT);
    Level level = levelDaoService.getOrCreate(Category.USER, 0, StatisticRefEnum.CREATED_ISSUE_COUNT, 0, 5);
    daoService.addLevelToUser(level, userWrapper);

    level = levelDaoService.getOrCreate(Category.USER, 1, StatisticRefEnum.CREATED_ISSUE_COUNT, 6, 15);
    daoService.addLevelToUser(level, userWrapper);

    final Level lastLevel = daoService.last(userWrapper, Category.USER);

    assertEquals(lastLevel, level);
  }

  @Test
  public void lastShouldReturnLastEarnedLevelsOrderedByDescendingCreationDate() throws Exception
  {
    final UserWrapper bob = userWrapperDaoService.create("bob", null);
    final UserWrapper patrick = userWrapperDaoService.create("patrick", null);
    statisticRefDaoService.getOrCreate(StatisticRefEnum.CREATED_ISSUE_COUNT);
    
    Level level = levelDaoService.getOrCreate(Category.USER, 0, StatisticRefEnum.CREATED_ISSUE_COUNT, 0, 5);
    daoService.addLevelToUser(level, bob);
    level = levelDaoService.getOrCreate(Category.USER, 1, StatisticRefEnum.CREATED_ISSUE_COUNT, 6, 15);
    daoService.addLevelToUser(level, bob);
    level = levelDaoService.getOrCreate(Category.USER, 2, StatisticRefEnum.CREATED_ISSUE_COUNT, 16, 30);
    daoService.addLevelToUser(level, bob);
    level = levelDaoService.getOrCreate(Category.USER, 0, StatisticRefEnum.CREATED_ISSUE_COUNT, 31, 50);
    daoService.addLevelToUser(level, patrick);

    final List<UserLevel> lastEarnedLevels = daoService.last(3);

    assertEquals(lastEarnedLevels.size(), 3);
    
    final UserLevel lastEarnedLevel = lastEarnedLevels.get(0);
    assertEquals(lastEarnedLevel.getUserWrapper(), patrick);
    assertEquals(lastEarnedLevel.getLevel().getLevelNumber(), 0);

    final UserLevel penultimateLevel = lastEarnedLevels.get(1);
    assertEquals(penultimateLevel.getUserWrapper(), bob);
    assertEquals(penultimateLevel.getLevel().getLevelNumber(), 2);
  }

  private UserWrapper createUserWrapper()
  {
    User user = new ImmutableUser(0, "bob", "Sponge Bob", null, true);
    return userWrapperDaoService.create(user);
  }
}
