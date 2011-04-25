package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.google.common.collect.ImmutableMap;
import com.madgnome.jira.plugins.jirachievements.data.ao.ReferencableEntity;
import com.madgnome.jira.plugins.jirachievements.data.services.IReferencableDaoService;

public abstract class ReferencableDaoService<T extends ReferencableEntity> extends BaseDaoService<T> implements IReferencableDaoService<T>
{
  public ReferencableDaoService(ActiveObjects ao)
  {
    super(ao);
  }

  public T get(String ref)
  {
    T[] references = ao.find(clazz, "REF = ?", ref);

    return references.length > 0 ? references[0] : null;
  }

  public T getOrCreate(String ref)
  {
    T[] references = ao.find(clazz, "REF = ?", ref);

    return references.length == 0 ? create(ref) : references[0];
  }

  public T create(String ref)
  {
    T referencable = ao.create(clazz, ImmutableMap.<String, Object>of("REF", ref));

    return referencable;
  }
}
