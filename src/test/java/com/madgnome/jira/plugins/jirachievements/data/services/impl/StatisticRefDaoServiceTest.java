package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRef;
import com.madgnome.jira.plugins.jirachievements.data.services.IStatisticRefDaoService;
import net.java.ao.sql.ActiveObjectSqlException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StatisticRefDaoServiceTest extends AbstractServiceTest
{
  private IStatisticRefDaoService statisticRefDaoService;

  @Before
  public void setUp() throws Exception
  {
    statisticRefDaoService = new StatisticRefDaoService(createActiveObjects());
  }

  @Test
  public void createShouldCreateAndReturnStatisticRef()
  {
    String ref = "TestStatistic";
    StatisticRef statisticRef = statisticRefDaoService.create(ref);
    assertNotNull(statisticRef);
    entityManager.flushAll();

    assertNotNull(statisticRefDaoService.get(ref));
  }

  @Test(expected = ActiveObjectSqlException.class)
  public void shouldNotCreateTwoStatisticRefWithSameRef()
  {
    String ref = "TestStatistic";
    statisticRefDaoService.create(ref);
    statisticRefDaoService.create(ref);
    entityManager.flushAll();
  }

  @Test
  public void getShouldReturnNullIfAnyStatisticWithRef()
  {
    assertNull(statisticRefDaoService.get("TestStatistic"));
  }

  @Test
  public void getShouldReturnStatisticRefWithRef()
  {
    String ref = "TestStatistic";
    statisticRefDaoService.create(ref);
    entityManager.flushAll();

    StatisticRef statisticRef = statisticRefDaoService.get(ref);
    assertNotNull(statisticRef);
    assertEquals(ref, statisticRef.getRef());
  }

  @Test
  public void getOrCreateShouldCreateIfAny()
  {
    String ref = "TestStatistic";
    assertNull(statisticRefDaoService.get(ref));
    StatisticRef statisticRef = statisticRefDaoService.getOrCreate(ref);
    assertNotNull(statisticRef);
    entityManager.flushAll();

    assertNotNull(statisticRefDaoService.get(ref));
  }

  @Test
  public void getOrCreateShouldReturnIfRefExists()
  {
    String ref = "TestStatistic";
    StatisticRef existingRef = statisticRefDaoService.create(ref);

    StatisticRef statisticRef = statisticRefDaoService.getOrCreate(ref);
    assertNotNull(statisticRef);
    assertEquals(existingRef, statisticRef);
  }

  @Test
  public void getOrCreateShouldCreateFirstThenReturn()
  {
    String ref = "TestStatistic";
    assertNull(statisticRefDaoService.get(ref));
    StatisticRef existingRef = statisticRefDaoService.getOrCreate(ref);
    assertNotNull(existingRef);

    StatisticRef statisticRef = statisticRefDaoService.getOrCreate(ref);
    assertNotNull(statisticRef);
    assertEquals(existingRef, statisticRef);
  }
}
