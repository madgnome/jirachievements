package com.madgnome.jira.plugins.jirachievements.data.services;

import com.madgnome.jira.plugins.jirachievements.data.ao.Category;
import com.madgnome.jira.plugins.jirachievements.data.ao.Level;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserLevel;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;

import java.util.List;

public interface IUserLevelDaoService extends IDaoService<UserLevel>
{
  void addLevelToUser(Level level, UserWrapper userWrapper);

  UserLevel get(Level level, UserWrapper userWrapper);
  UserLevel get(int levelId, int userWrapperId);
  
  Level last(UserWrapper userWrapper, Category category);

  List<UserLevel> last(int maxResult);
}
