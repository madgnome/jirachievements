package com.madgnome.jira.plugins.jirachievements.data.upgrades.v1;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.activeobjects.external.ActiveObjectsUpgradeTask;
import com.atlassian.activeobjects.external.ModelVersion;
import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.user.util.UserUtil;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;

public class InitDatabaseTask implements ActiveObjectsUpgradeTask
{
  private final UserUtil userUtil;
  private final IUserWrapperDaoService userWrapperDaoService;

  public InitDatabaseTask(IUserWrapperDaoService userWrapperDaoService, UserUtil userUtil)
  {
    this.userWrapperDaoService = userWrapperDaoService;
    this.userUtil = userUtil;
  }

  @Override
  public ModelVersion getModelVersion()
  {
    return ModelVersion.valueOf(String.valueOf(0));
  }

  @Override
  public void upgrade(ModelVersion currentVersion, ActiveObjects ao)
  {
    for (User user : userUtil.getUsers())
    {
      userWrapperDaoService.createUserWrapper(user);
    }
  }
}
