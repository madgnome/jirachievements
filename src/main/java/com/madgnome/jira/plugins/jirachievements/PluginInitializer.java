package com.madgnome.jira.plugins.jirachievements;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.user.util.UserUtil;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserDaoService;
import org.springframework.beans.factory.InitializingBean;

public class PluginInitializer implements InitializingBean
{
  private final UserUtil userUtil;
  private final IUserDaoService userDaoService;

  public PluginInitializer(IUserDaoService userDaoService, UserUtil userUtil)
  {
    this.userDaoService = userDaoService;
    this.userUtil = userUtil;
  }

  @Override
  public void afterPropertiesSet() throws Exception
  {
    initDatabase();
  }

  private void initDatabase()
  {
    initUserWrappers();
  }

  private void initUserWrappers()
  {
    for (User user : userUtil.getUsers())
    {
      userDaoService.createUserWrapper(user);
    }
  }
}
