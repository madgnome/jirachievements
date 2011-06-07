package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.crowd.embedded.api.User;
import com.google.common.collect.ImmutableMap;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.Action;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;
import net.java.ao.ActiveObjectsException;

public class UserWrapperDaoService extends BaseDaoService<UserWrapper> implements IUserWrapperDaoService
{
  @Override
  protected Class<UserWrapper> getClazz()
  {
    return UserWrapper.class;
  }

  public UserWrapperDaoService(ActiveObjects ao)
  {
    super(ao);
  }

  @Override
  public UserWrapper getOrCreate(User jiraUser)
  {
    return getOrCreate(jiraUser.getName());
  }

  public UserWrapper getOrCreate(String jiraUserName)
  {
    return getOrCreate(jiraUserName, null);
  }

  @Override
  public UserWrapper getOrCreate(String jiraUserName, Action<UserWrapper> postCreateAction)
  {
    UserWrapper userWrapper = get(jiraUserName);

    return userWrapper == null ? create(jiraUserName, postCreateAction) : userWrapper;
  }

  @Override
  public UserWrapper create(User jiraUser)
  {
    return create(jiraUser.getName(), null);
  }

  @Override
  public UserWrapper create(String jiraUserName, Action<UserWrapper> postCreateAction)
  {
    try
    {
      UserWrapper userWrapper = ao.create(UserWrapper.class, ImmutableMap.<String, Object>of("JIRA_USER_NAME", jiraUserName));
      if (postCreateAction != null)
      {
        postCreateAction.execute(userWrapper);
      }

      return userWrapper;
    }
    catch (ActiveObjectsException e)
    {
      logger.debug("Try to create an existing userWrapper", e);
    }

    return get(jiraUserName);
  }

  @Override
  public UserWrapper get(User jiraUser)
  {
    return get(jiraUser.getName());
  }

  @Override
  public UserWrapper get(String jiraUserName)
  {
    UserWrapper[] userWrappers = ao.find(UserWrapper.class, "JIRA_USER_NAME = ?", jiraUserName);
    return userWrappers.length > 0 ? userWrappers[0] : null;
  }

  @Override
  public void activate(User jiraUser, boolean active)
  {
    UserWrapper userWrapper = get(jiraUser);
    if (userWrapper != null)
    {
      userWrapper.setActive(active);
      userWrapper.save();
    }
  }
}
