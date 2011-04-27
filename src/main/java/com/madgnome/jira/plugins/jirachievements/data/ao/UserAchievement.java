package com.madgnome.jira.plugins.jirachievements.data.ao;

import net.java.ao.Entity;
import net.java.ao.schema.Default;

import java.util.Date;

public interface UserAchievement extends Entity
{
  UserWrapper getUserWrapper();
  void setUserWrapper(UserWrapper userWrapper);

  Achievement getAchievement();
  void setAchievement(Achievement achievement);

  @Default("false")
  boolean isNotified();
  void setNotified(boolean notified);

  @Default("CURRENT_TIMESTAMP")
  Date getCreatedOn();
  void setCreatedOn(Date createdOn);
}
