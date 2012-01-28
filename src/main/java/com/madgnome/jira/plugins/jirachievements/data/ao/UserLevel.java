package com.madgnome.jira.plugins.jirachievements.data.ao;

import java.util.Date;

public interface UserLevel extends KeyableEntity
{
  UserWrapper getUserWrapper();
  void setUserWrapper(UserWrapper userWrapper);
  
  Level getLevel();
  void setLevel(Level level);
  
  Date getCreatedOn();
  void setCreatedOn(Date createdOn);
}
