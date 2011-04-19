package com.madgnome.jira.plugins.jirachievements.data.ao;

import net.java.ao.Entity;

public interface UserAchievement extends Entity
{
  int getUserWrapper();
  void setUserWrapper(UserWrapper userWrapper);

  Achievement getAchievement();
  void setAchievement(Achievement achievement);

  boolean isNotified();
  void setNotified(boolean notified);
}
