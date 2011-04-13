package com.madgnome.jira.plugins.jirachievements.data.ao;

import net.java.ao.Entity;
import net.java.ao.OneToMany;

import java.util.List;

public interface UserAchievements extends Entity
{
  int getUserId();
  void setUserId(int userId);

  @OneToMany
  List<Achievement> getAchievements();
}
