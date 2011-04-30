package com.madgnome.jira.plugins.jirachievements.data.services;

import net.java.ao.Entity;

public interface IReferencableDaoService<T extends Entity> extends IDaoService<T>
{
  T get(Enum enumValue);
  T get(String ref);

  T getOrCreate(Enum enumValue);
  T getOrCreate(String ref);

  T create(Enum enumValue);
  T create(String ref);
}
