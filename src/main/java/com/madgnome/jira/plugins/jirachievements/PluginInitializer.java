package com.madgnome.jira.plugins.jirachievements;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.extension.Startable;
import com.atlassian.jira.user.util.UserUtil;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;
import com.madgnome.jira.plugins.jirachievements.rules.WelcomeRule;
import com.madgnome.jira.plugins.jirachievements.utils.initializers.ITableInitializer;

import java.util.List;

public class PluginInitializer implements Startable
{
  // TODO Replace by an ordered list of ITableInitializer
  private final List<ITableInitializer> tableInitializers;
  private final UserUtil userUtil;
  private final IUserWrapperDaoService userWrapperDaoService;

  private final WelcomeRule welcomeRule;

  public PluginInitializer(IUserWrapperDaoService userWrapperDaoService, UserUtil userUtil, WelcomeRule welcomeRule, List<ITableInitializer> tableInitializers)
  {
    this.userWrapperDaoService = userWrapperDaoService;
    this.userUtil = userUtil;
    this.tableInitializers = tableInitializers;
    this.welcomeRule = welcomeRule;
  }

  @Override
  public void start() throws Exception
  {
    initDatabase();
  }

  private void initDatabase()
  {
    for (ITableInitializer tableInitializer : tableInitializers)
    {
      tableInitializer.initialize();
    }

    initUserWrappers();
  }

  private void initUserWrappers()
  {
    for (User user : userUtil.getUsers())
    {
      if (userWrapperDaoService.get(user) == null)
      {
        userWrapperDaoService.create(user);

        welcomeRule.execute(user);
      }
    }
  }
}
