package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.crowd.embedded.api.User;
import com.atlassian.crowd.embedded.impl.ImmutableUser;
import com.madgnome.jira.plugins.jirachievements.data.ao.ProjectStatistic;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRef;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.IStatisticRefDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ProjectStatisticDaoServiceTest extends BaseDaoServiceTest<ProjectStatistic, ProjectStatisticDaoService>
{
  private IStatisticRefDaoService statisticRefDaoService;
  private IUserWrapperDaoService userWrapperDaoService;

  @Before
  public void setUp() throws Exception
  {
    ActiveObjects ao = createActiveObjects();
    statisticRefDaoService = new StatisticRefDaoService(ao);
    userWrapperDaoService = new UserWrapperDaoService(ao);

    daoService = new ProjectStatisticDaoService(ao, statisticRefDaoService);
  }

  @Test
  public void getShouldReturnNullIfStatisticRefDoesntExist()
  {
    UserWrapper userWrapper = createUserWrapper();
    assertNull(daoService.get(userWrapper, "PKEY", StatisticRefEnum.CREATED_ISSUE_COUNT));
  }

  @Test
  public void createOrUpdateShouldCreateStatisticWithValueIfAny()
  {
    StatisticRefEnum statRef = StatisticRefEnum.CREATED_ISSUE_COUNT;
    createStatisticRef(statRef);
    UserWrapper userWrapper = createUserWrapper();

    int value = 1;
    String projectKey = "PKEY";
    daoService.createOrUpdate(userWrapper, projectKey, statRef, value);
    ProjectStatistic projectStatistic = daoService.get(userWrapper, projectKey, statRef);
    assertNotNull(projectStatistic);
    assertEquals(value, projectStatistic.getValue());
  }

  @Test
  public void createOrUpdateShouldUpdateStatisticWithValueIfAny()
  {
    StatisticRefEnum statRef = StatisticRefEnum.CREATED_ISSUE_COUNT;
    createStatisticRef(statRef);
    UserWrapper userWrapper = createUserWrapper();

    int value = 2;
    String projectKey = "PKEY";
    daoService.createOrUpdate(userWrapper, projectKey, statRef, 1);
    daoService.createOrUpdate(userWrapper, projectKey, statRef, value);
    ProjectStatistic projectStatistic = daoService.get(userWrapper, projectKey, statRef);
    assertNotNull(projectStatistic);
    assertEquals(value, projectStatistic.getValue());
  }

  @Test
  public void findShouldReturnStatisticsForProjectOrderedByDescendingValue() throws Exception
  {
    StatisticRefEnum statRef = StatisticRefEnum.CREATED_ISSUE_COUNT;
    createStatisticRef(statRef);
    UserWrapper bobUserWrapper = createUserWrapper("bob", "Sponge Bob");
    UserWrapper patrickUserWrapper = createUserWrapper("patrick", "Patrick Star");

    String projectKey = "PKEY";
    daoService.createOrUpdate(bobUserWrapper, projectKey, statRef, 1);
    daoService.createOrUpdate(patrickUserWrapper, projectKey, statRef, 5);
    entityManager.flushAll();

    List<ProjectStatistic> projectStatistics = daoService.findStatisticsForProjectAndRef(projectKey, statRef);
    assertNotNull(projectStatistics);
    assertEquals(2, projectStatistics.size());
    assertTrue( projectStatistics.get(0).getValue() >= projectStatistics.get(1).getValue());
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
