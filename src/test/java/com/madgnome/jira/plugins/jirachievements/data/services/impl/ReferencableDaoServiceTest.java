package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.madgnome.jira.plugins.jirachievements.data.ao.ReferencableEntity;
import com.madgnome.jira.plugins.jirachievements.data.services.IReferencableDaoService;
import net.java.ao.ActiveObjectsException;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class ReferencableDaoServiceTest<T extends ReferencableEntity, V extends IReferencableDaoService<T, ?>> extends AbstractServiceTest
{
  protected V referencableDaoService;

  @Test
  public void createShouldCreateAndReturnReferencable()
  {
    String ref = "Reference";
    T statisticRef = referencableDaoService.create(ref);
    assertNotNull(statisticRef);
    entityManager.flushAll();

    assertNotNull(referencableDaoService.get(ref));
  }

  @Test(expected = ActiveObjectsException.class)
  public void shouldNotCreateTwoStatisticRefWithSameRef()
  {
    String ref = "Reference";
    referencableDaoService.create(ref);
    referencableDaoService.create(ref);
    entityManager.flushAll();
  }

  @Test
  public void getShouldReturnNullIfAnyStatisticWithRef()
  {
    assertNull(referencableDaoService.get("TestStatistic"));
  }

  @Test
  public void getShouldReturnStatisticRefWithRef()
  {
    String ref = "Reference";
    referencableDaoService.create(ref);
    entityManager.flushAll();

    T referencable = referencableDaoService.get(ref);
    assertNotNull(referencable);
    assertEquals(ref, referencable.getRef());
  }

  @Test
  public void getOrCreateShouldCreateIfAny()
  {
    String ref = "Reference";
    assertNull(referencableDaoService.get(ref));
    T referencable = referencableDaoService.getOrCreate(ref);
    assertNotNull(referencable);
    entityManager.flushAll();

    assertNotNull(referencableDaoService.get(ref));
  }

  @Test
  public void getOrCreateShouldReturnIfRefExists()
  {
    String ref = "Reference";
    T existingRef = referencableDaoService.create(ref);

    T T = referencableDaoService.getOrCreate(ref);
    assertNotNull(T);
    assertEquals(existingRef, T);
  }

  @Test
  public void getOrCreateShouldCreateFirstThenReturn()
  {
    String ref = "Reference";
    assertNull(referencableDaoService.get(ref));
    T existingRef = referencableDaoService.getOrCreate(ref);
    assertNotNull(existingRef);

    T referencable = referencableDaoService.getOrCreate(ref);
    assertNotNull(referencable);
    assertEquals(existingRef, referencable);
  }
}
