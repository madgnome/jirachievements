package com.madgnome.jira.plugins.jirachievements.data.ao;

import net.java.ao.Entity;
import net.java.ao.ManyToMany;
import net.java.ao.Preload;

@Preload
public interface UserWrapper extends Entity
{
  public String getJiraUserName();
  public void setJiraUserName(String name);

  @ManyToMany(UserAchievements.class)
  public Achievement[] getAchievements();
}
