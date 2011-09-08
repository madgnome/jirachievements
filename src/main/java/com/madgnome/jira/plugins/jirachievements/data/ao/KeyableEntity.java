package com.madgnome.jira.plugins.jirachievements.data.ao;

import net.java.ao.Entity;
import net.java.ao.Polymorphic;
import net.java.ao.schema.Indexed;
import net.java.ao.schema.Unique;

@Polymorphic
public interface KeyableEntity extends Entity
{
  @Indexed
  @Unique
  String getKey();
  void setKey(String key);
}
