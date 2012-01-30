package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.crowd.embedded.api.User;
import com.atlassian.crowd.embedded.impl.ImmutableUser;
import com.madgnome.jira.plugins.jirachievements.data.ao.ComponentStatistic;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRef;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.IStatisticRefDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ProjectComponentStatisticDaoServiceTest extends BaseDaoServiceTest<ComponentStatistic, ProjectComponentStatisticDaoService>
{
  private IStatisticRefDaoService statisticRefDaoService;
  private IUserWrapperDaoService userWrapperDaoService;

  @Before
  public void setUp() throws Exception
  {
    ActiveObjects ao = createActiveObjects();
    statisticRefDaoService = new StatisticRefDaoService(ao);
    userWrapperDaoService = new UserWrapperDaoService(ao);

    daoService = new ProjectComponentStatisticDaoService(ao, statisticRefDaoService);
  }

  @Test
  public void getShouldReturnNullIfStatisticRefDoesntExist()
  {
    UserWrapper userWrapper = createUserWrapper();
    assertNull(daoService.get(userWrapper, "PKEY", "Config", StatisticRefEnum.CREATED_ISSUE_COUNT));
  }

  @Test
  public void createOrUpdateShouldCreateStatisticWithValueIfAny()
  {
    StatisticRefEnum statRef = StatisticRefEnum.CREATED_ISSUE_COUNT;
    createStatisticRef(statRef);
    UserWrapper userWrapper = createUserWrapper();

    int value = 1;
    final String projectKey = "PKEY";
    final String component = "Config";
    daoService.createOrUpdate(userWrapper, projectKey, component, statRef, value);
    ComponentStatistic componentStatistic = daoService.get(userWrapper, projectKey, component, statRef);
    assertNotNull(componentStatistic);
    assertEquals(value, componentStatistic.getValue());
  }

  @Test
  public void createOrUpdateShouldUpdateStatisticWithValueIfAny()
  {
    StatisticRefEnum statRef = StatisticRefEnum.CREATED_ISSUE_COUNT;
    createStatisticRef(statRef);
    UserWrapper userWrapper = createUserWrapper();

    int value = 2;
    final String projectKey = "PKEY";
    final String comppnent = "Config";
    daoService.createOrUpdate(userWrapper, projectKey, comppnent, statRef, 1);
    daoService.createOrUpdate(userWrapper, projectKey, comppnent, statRef, value);
    ComponentStatistic componentStatistic = daoService.get(userWrapper, projectKey, comppnent, statRef);
    assertNotNull(componentStatistic);
    assertEquals(value, componentStatistic.getValue());
  }

  @Test
  public void findShouldReturnStatisticsForProjectOrderedByDescendingValue() throws Exception
  {
    StatisticRefEnum statRef = StatisticRefEnum.CREATED_ISSUE_COUNT;
    createStatisticRef(statRef);
    UserWrapper bobUserWrapper = createUserWrapper("bob", "Sponge Bob");
    UserWrapper patrickUserWrapper = createUserWrapper("patrick", "Patrick Star");

    final String projectKey = "PKEY";
    final String component = "Config";
    daoService.createOrUpdate(bobUserWrapper, projectKey, component, statRef, 1);
    daoService.createOrUpdate(patrickUserWrapper, projectKey, component, statRef, 5);
    entityManager.flushAll();

    List<ComponentStatistic> componentStatistics = daoService.findStatisticsForComponentAndRef(projectKey, component, statRef);
    assertNotNull(componentStatistics);
    assertEquals(2, componentStatistics.size());
    assertTrue(componentStatistics.get(0).getValue() >= componentStatistics.get(1).getValue());
  }

  private StatisticRef createStatisticRef(StatisticRefEnum statRef)
  {
    return statisticRefDaoService.create(statRef);
  }

  private UserWrapper createUserWrapper()
  {
    return createUserWrapper("bob", "Sponge Bog");
  }

  private UserWrapper createUserWrapper(String name, String displayName)
  {
    User user = new ImmutableUser(0, name, displayName, null, true);
    return userWrapperDaoService.create(user);
  }
}
