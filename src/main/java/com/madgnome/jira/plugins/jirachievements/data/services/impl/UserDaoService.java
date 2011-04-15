package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.crowd.embedded.api.User;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserDaoService;
import net.java.ao.Query;

public class UserDaoService extends BaseDaoService implements IUserDaoService
{
  public UserDaoService(ActiveObjects ao)
  {
    super(ao);
  }

  @Override
  public UserWrapper createUserWrapper(User jiraUser)
  {
    UserWrapper userWrapper = ao.create(UserWrapper.class);
    userWrapper.setJiraUserName(jiraUser.getName());
    userWrapper.save();

    return userWrapper;
  }

  @Override
  public UserWrapper getUserWrapper(User jiraUser)
  {
    Query query = Query.select().where("jiraUserName = ?", jiraUser.getName());
    return ao.find(UserWrapper.class, query)[0];
  }
}
