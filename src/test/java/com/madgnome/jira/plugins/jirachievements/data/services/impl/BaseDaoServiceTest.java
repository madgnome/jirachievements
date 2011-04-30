package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.madgnome.jira.plugins.jirachievements.data.services.IDaoService;
import net.java.ao.Entity;
import org.junit.Test;

import static org.junit.Assert.assertNull;

public abstract class BaseDaoServiceTest<T extends Entity, V extends IDaoService<T>> extends AbstractServiceTest
{
  protected V daoService;

  @Test
  public void getShouldNullIfAny()
  {
    assertNull(daoService.get(0));
  }
}
