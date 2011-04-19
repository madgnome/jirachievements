package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.madgnome.jira.plugins.jirachievements.data.services.IDaoService;
import net.java.ao.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public abstract class BaseDaoService<T extends Entity> implements IDaoService<T>
{
  protected Class<T> clazz = getClazz();
  protected abstract Class<T> getClazz();

  protected static final Logger logger = LoggerFactory.getLogger(BaseDaoService.class);
  protected final ActiveObjects ao;

  public BaseDaoService(ActiveObjects ao)
  {
    this.ao = ao;
  }

  public T get(int id)
  {
    T[] result = ao.find(clazz, "ID = ?", id);
    return result.length > 0 ? result[0] : null;
  }

  public List<T> all()
  {
    return Arrays.asList(ao.find(clazz));
  }
}
