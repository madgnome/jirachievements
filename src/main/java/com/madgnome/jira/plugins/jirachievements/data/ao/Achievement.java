package com.madgnome.jira.plugins.jirachievements.data.ao;

import net.java.ao.Entity;
import net.java.ao.ManyToMany;
import net.java.ao.Preload;

@Preload
public interface Achievement extends Entity
{
  public String getName();
  public void setName(String name);

  public String getDescription();
  public void setDescription(String description);

  @ManyToMany(UserAchievements.class)
  public UserWrapper[] getUsers();
}
