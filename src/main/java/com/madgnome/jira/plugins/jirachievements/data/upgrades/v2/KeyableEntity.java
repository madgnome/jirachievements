package com.madgnome.jira.plugins.jirachievements.data.upgrades.v2;

import net.java.ao.Entity;
import net.java.ao.Polymorphic;

@Polymorphic
public interface KeyableEntity extends Entity
{
  String getKey();
  void setKey(String key);
}
