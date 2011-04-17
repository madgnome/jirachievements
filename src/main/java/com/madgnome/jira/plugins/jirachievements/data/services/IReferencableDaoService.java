package com.madgnome.jira.plugins.jirachievements.data.services;

import net.java.ao.Entity;

public interface IReferencableDaoService<T extends Entity> extends IDaoService<T>
{
  public T getOrCreate(String ref);
  public T create(String ref);
}
