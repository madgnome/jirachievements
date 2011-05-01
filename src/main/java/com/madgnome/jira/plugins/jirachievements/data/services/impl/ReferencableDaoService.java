package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.google.common.collect.ImmutableMap;
import com.madgnome.jira.plugins.jirachievements.data.ao.ReferencableEntity;
import com.madgnome.jira.plugins.jirachievements.data.services.IReferencableDaoService;

public abstract class ReferencableDaoService<T extends ReferencableEntity, V extends Enum> extends BaseDaoService<T> implements IReferencableDaoService<T, V>
{
  public ReferencableDaoService(ActiveObjects ao)
  {
    super(ao);
  }

  public T get(V enumValue)
  {
    return get(enumValue.toString());
  }

  public T get(String ref)
  {
    T[] references = ao.find(clazz, "REF = ?", ref);

    return references.length > 0 ? references[0] : null;
  }

  public T getOrCreate(V enumValue)
  {
    return getOrCreate(enumValue.toString());
  }

  public T getOrCreate(String ref)
  {
    T reference = get(ref);

    return reference == null ? create(ref) : reference;
  }

  public T create(V enumValue)
  {
    return getOrCreate(enumValue.toString());
  }

  public T create(String ref)
  {
    T referencable = ao.create(clazz, ImmutableMap.<String, Object>of("REF", ref));

    return referencable;
  }
}
