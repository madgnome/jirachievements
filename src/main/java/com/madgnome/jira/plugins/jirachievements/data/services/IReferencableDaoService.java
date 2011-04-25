package com.madgnome.jira.plugins.jirachievements.data.services;

import net.java.ao.Entity;

public interface IReferencableDaoService<T extends Entity> extends IDaoService<T>
{
  T get(String ref);
  T getOrCreate(String ref);
  T create(String ref);
}
