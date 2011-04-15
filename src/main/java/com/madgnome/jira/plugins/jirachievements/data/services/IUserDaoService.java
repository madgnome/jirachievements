package com.madgnome.jira.plugins.jirachievements.data.services;

import com.atlassian.activeobjects.tx.Transactional;
import com.atlassian.crowd.embedded.api.User;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;

@Transactional
public interface IUserDaoService
{
  UserWrapper createUserWrapper(User jiraUser);
  UserWrapper getUserWrapper(User jiraUser);
}
