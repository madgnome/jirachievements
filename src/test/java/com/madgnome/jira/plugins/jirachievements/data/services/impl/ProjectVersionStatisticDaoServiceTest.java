package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.crowd.embedded.api.User;
import com.atlassian.crowd.embedded.impl.ImmutableUser;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRef;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.ao.VersionStatistic;
import com.madgnome.jira.plugins.jirachievements.data.services.IStatisticRefDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ProjectVersionStatisticDaoServiceTest extends BaseDaoServiceTest<VersionStatistic, ProjectVersionStatisticDaoService>
{
  private IStatisticRefDaoService statisticRefDaoService;
  private IUserWrapperDaoService userWrapperDaoService;

  @Before
  public void setUp() throws Exception
  {
    ActiveObjects ao = createActiveObjects();
    statisticRefDaoService = new StatisticRefDaoService(ao);
    userWrapperDaoService = new UserWrapperDaoService(ao);

    daoService = new ProjectVersionStatisticDaoService(ao, statisticRefDaoService);
  }

  @Test
  public void getShouldReturnNullIfStatisticRefDoesntExist()
  {
    UserWrapper userWrapper = createUserWrapper();
    assertNull(daoService.get(userWrapper, "PKEY", "1.0", StatisticRefEnum.CREATED_ISSUE_COUNT));
  }

  @Test
  public void createOrUpdateShouldCreateStatisticWithValueIfAny()
  {
    StatisticRefEnum statRef = StatisticRefEnum.CREATED_ISSUE_COUNT;
    createStatisticRef(statRef);
    UserWrapper userWrapper = createUserWrapper();

    int value = 1;
    final String projectKey = "PKEY";
    final String version = "1.0";
    daoService.createOrUpdate(userWrapper, projectKey, version, statRef, value);
    VersionStatistic versionStatistic = daoService.get(userWrapper, projectKey, version, statRef);
    assertNotNull(versionStatistic);
    assertEquals(value, versionStatistic.getValue());
  }

  @Test
  public void createOrUpdateShouldUpdateStatisticWithValueIfAny()
  {
    StatisticRefEnum statRef = StatisticRefEnum.CREATED_ISSUE_COUNT;
    createStatisticRef(statRef);
    UserWrapper userWrapper = createUserWrapper();

    int value = 2;
    final String projectKey = "PKEY";
    final String version = "1.0";
    daoService.createOrUpdate(userWrapper, projectKey, version, statRef, 1);
    daoService.createOrUpdate(userWrapper, projectKey, version, statRef, value);
    VersionStatistic versionStatistic = daoService.get(userWrapper, projectKey, version, statRef);
    assertNotNull(versionStatistic);
    assertEquals(value, versionStatistic.getValue());
  }

  @Test
  public void findShouldReturnStatisticsForProjectOrderedByDescendingValue() throws Exception
  {
    StatisticRefEnum statRef = StatisticRefEnum.CREATED_ISSUE_COUNT;
    createStatisticRef(statRef);
    UserWrapper bobUserWrapper = createUserWrapper("bob", "Sponge Bob");
    UserWrapper patrickUserWrapper = createUserWrapper("patrick", "Patrick Star");

    final String projectKey = "PKEY";
    final String version = "1.0";
    daoService.createOrUpdate(bobUserWrapper, projectKey, version, statRef, 1);
    daoService.createOrUpdate(patrickUserWrapper, projectKey, version, statRef, 5);
    entityManager.flushAll();

    List<VersionStatistic> versionStatistics = daoService.findStatisticsForVersionAndRef(projectKey, version, statRef);
    assertNotNull(versionStatistics);
    assertEquals(2, versionStatistics.size());
    assertTrue(versionStatistics.get(0).getValue() >= versionStatistics.get(1).getValue());
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
