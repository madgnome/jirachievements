package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.crowd.embedded.impl.ImmutableUser;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRef;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserStatistic;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserStatisticDaoServiceTest extends BaseDaoServiceTest<UserStatistic, UserStatisticDaoService>
{
  private StatisticRefDaoService statisticRefDaoService;
  private UserWrapperDaoService userWrapperDaoService;

  @Before
  public void setUp() throws Exception
  {
    statisticRefDaoService = new StatisticRefDaoService(createActiveObjects());
    userWrapperDaoService = new UserWrapperDaoService(createActiveObjects());

    daoService = new UserStatisticDaoService(createActiveObjects(), statisticRefDaoService);
  }

  @Test
  public void getShouldReturnNullIfStatisticRefDoesntExist()
  {
    UserWrapper userWrapper = createUserWrapper();
    assertNull(daoService.get(userWrapper, StatisticRefEnum.CREATED_ISSUE_COUNT));
  }

  @Test
  public void getShouldReturnStatisticWithValueAtZeroIfAny()
  {
    StatisticRefEnum statRef = StatisticRefEnum.CREATED_ISSUE_COUNT;
    createStatisticRef(statRef);
    UserWrapper userWrapper = createUserWrapper();

    UserStatistic userStatistic = daoService.get(userWrapper, statRef);
    assertNotNull(userStatistic);
    assertEquals(0, userStatistic.getValue());
  }

  @Test
  public void createOrUpdateShouldCreateStatisticWithValueIfAny()
  {
    StatisticRefEnum statRef = StatisticRefEnum.CREATED_ISSUE_COUNT;
    createStatisticRef(statRef);
    UserWrapper userWrapper = createUserWrapper();

    int value = 1;
    daoService.createOrUpdate(statRef, userWrapper, value);
    UserStatistic userStatistic = daoService.get(userWrapper, statRef);
    assertNotNull(userStatistic);
    assertEquals(value, userStatistic.getValue());
  }

  @Test
  public void createOrUpdateShouldUpdateStatisticWithValueIfAny()
  {
    StatisticRefEnum statRef = StatisticRefEnum.CREATED_ISSUE_COUNT;
    createStatisticRef(statRef);
    UserWrapper userWrapper = createUserWrapper();

    int value = 2;
    daoService.createOrUpdate(statRef, userWrapper, 1);
    daoService.createOrUpdate(statRef, userWrapper, value);
    UserStatistic userStatistic = daoService.get(userWrapper, statRef);
    assertNotNull(userStatistic);
    assertEquals(value, userStatistic.getValue());
  }

  private StatisticRef createStatisticRef(StatisticRefEnum statisticRefEnum)
  {
    return statisticRefDaoService.create(statisticRefEnum);
  }

  private UserWrapper createUserWrapper()
  {
    User user = new ImmutableUser(0, "bob", "Sponge Bob", null, true);
    return userWrapperDaoService.create(user);
  }
}
