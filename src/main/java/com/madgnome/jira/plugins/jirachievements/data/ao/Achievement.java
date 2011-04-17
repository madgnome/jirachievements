package com.madgnome.jira.plugins.jirachievements.data.ao;

import net.java.ao.Entity;
import net.java.ao.ManyToMany;

public interface Achievement extends Entity, ReferencableEntity
{
  public String getName();
  public void setName(String name);

  public String getDescription();
  public void setDescription(String description);

  @ManyToMany(UserAchievement.class)
  public UserWrapper[] getUsers();

}
