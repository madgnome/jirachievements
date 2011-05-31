package com.madgnome.jira.plugins.jirachievements.data.ao;

import net.java.ao.Entity;
import net.java.ao.Polymorphic;
import net.java.ao.schema.Indexed;
import net.java.ao.schema.NotNull;
import net.java.ao.schema.Unique;

@Polymorphic
public interface ReferencableEntity extends Entity
{
  @NotNull
  @Indexed
  @Unique
  public String getRef();
  public void setRef(String ref);
}
