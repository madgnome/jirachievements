package com.madgnome.jira.plugins.jirachievements.data.services;

import net.java.ao.Entity;

public interface IReferencableDaoService<T extends Entity, V extends Enum> extends IDaoService<T>
{
  T get(V enumValue);
  T get(String ref);

  T getOrCreate(V enumValue);
  T getOrCreate(String ref);

  T create(V enumValue);
  T create(String ref);
}
