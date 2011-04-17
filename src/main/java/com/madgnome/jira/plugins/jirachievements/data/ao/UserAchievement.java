package com.madgnome.jira.plugins.jirachievements.data.ao;

import net.java.ao.Entity;

public interface UserAchievement extends Entity
{
  public int getUserWrapper();
  public void setUserWrapper(UserWrapper userWrapper);

  public Achievement getAchievement();
  public void setAchievement(Achievement achievement);
}
