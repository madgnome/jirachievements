package com.madgnome.jira.plugins.jirachievements.data.ao;

import net.java.ao.Entity;
import net.java.ao.schema.Indexed;
import net.java.ao.schema.NotNull;
import net.java.ao.schema.Unique;

public interface ReferencableEntity extends Entity
{
  @Indexed
  @NotNull
  @Unique
  public String getRef();
  public void setRef(String ref);
}
