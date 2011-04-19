package com.madgnome.jira.plugins.jirachievements.data.ao;

import net.java.ao.Entity;
import net.java.ao.ManyToMany;

public interface Achievement extends Entity, ReferencableEntity
{
  String getName();
  void setName(String name);

  String getDescription();
  void setDescription(String description);

  @ManyToMany(UserAchievement.class)
  UserWrapper[] getUsers();
}
