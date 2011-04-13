package com.madgnome.jira.plugins.jirachievements.data.ao;

import net.java.ao.Entity;
import net.java.ao.Preload;

@Preload
public interface Achievement extends Entity
{
  String getName();
  void setName(String name);

  String getDescription();
  void setDescription(String Description);
}
