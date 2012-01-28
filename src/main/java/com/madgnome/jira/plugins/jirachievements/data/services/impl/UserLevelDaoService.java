package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.google.common.collect.ImmutableMap;
import com.madgnome.jira.plugins.jirachievements.data.ao.Category;
import com.madgnome.jira.plugins.jirachievements.data.ao.Level;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserLevel;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserLevelDaoService;
import com.madgnome.jira.plugins.jirachievements.data.utils.KeyableUtils;
import net.java.ao.Query;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class UserLevelDaoService extends BaseDaoService<UserLevel> implements IUserLevelDaoService
{
  @Override
  protected Class<UserLevel> getClazz()
  {
    return UserLevel.class;
  }

  public UserLevelDaoService(ActiveObjects ao)
  {
    super(ao);
  }

  @Override
  public void addLevelToUser(Level level, UserWrapper userWrapper)
  {
    if (get(level, userWrapper) == null)
    {
      try
      {
        createUserLevel(level, userWrapper);
      }
      catch (Exception e)
      {
        logger.debug("Try to create an existing userlevel", e);
      }
    }
  }
  
  private UserLevel createUserLevel(Level level, UserWrapper userWrapper)
  {
    return ao.create(getClazz(), new ImmutableMap.Builder<String, Object>()
            .put("KEY", KeyableUtils.buildKey(userWrapper, level))
            .put("USER_WRAPPER_ID", userWrapper.getID())
            .put("LEVEL_ID", level.getID())
            .put("CREATED_ON", new Date())
            .build());
  }

  @Override
  public UserLevel get(Level level, UserWrapper userWrapper)
  {
    return get(level.getID(), userWrapper.getID());
  }

  @Override
  public UserLevel get(int levelId, int userWrapperId)
  {
    return null;
  }

  @Override
  public Level last(UserWrapper userWrapper, Category category)
  {
    UserLevel[] userLevel = ao.find(getClazz(), Query.select()
            .alias(UserLevel.class, "ul")
            .alias(Level.class, "l")
            .join(Level.class, "ul.LEVEL_ID = l.ID")
            .where("USER_WRAPPER_ID = ? AND CATEGORY = ?", userWrapper.getID(), category)
            .order("LEVEL_NUMBER DESC"));
    if (userLevel != null && userLevel.length > 0)
    {
      return userLevel[0].getLevel();
    }

    return null;
  }

  @Override
  public List<UserLevel> last(int maxResult)
  {
    return Arrays.asList(ao.find(getClazz(), Query.select().order("CREATED_ON DESC").limit(maxResult)));
  }
}
