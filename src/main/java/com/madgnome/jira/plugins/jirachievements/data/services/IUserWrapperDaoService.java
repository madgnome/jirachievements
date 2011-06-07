package com.madgnome.jira.plugins.jirachievements.data.services;

import com.atlassian.activeobjects.tx.Transactional;
import com.atlassian.crowd.embedded.api.User;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;

@Transactional
public interface IUserWrapperDaoService extends IDaoService<UserWrapper>
{
  UserWrapper create(User jiraUser);
  UserWrapper create(String jiraUserName);
  UserWrapper getOrCreate(User jiraUser);
  UserWrapper getOrCreate(String jiraUserName);
  UserWrapper get(User jiraUser);
  UserWrapper get(String jiraUserName);

  void activate(User jiraUser, boolean active);
}
