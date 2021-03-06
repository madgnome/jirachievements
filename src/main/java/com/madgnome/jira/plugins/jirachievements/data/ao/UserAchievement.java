package com.madgnome.jira.plugins.jirachievements.data.ao;

import net.java.ao.Entity;
import net.java.ao.Preload;
import net.java.ao.schema.Default;

import java.util.Date;

@Preload("NOTIFIED")
public interface UserAchievement extends Entity
{
  UserWrapper getUserWrapper();
  void setUserWrapper(UserWrapper userWrapper);

  Achievement getAchievement();
  void setAchievement(Achievement achievement);

  @Default("false")
  boolean isNotified();
  void setNotified(boolean notified);

  Date getCreatedOn();
  void setCreatedOn(Date createdOn);
}
