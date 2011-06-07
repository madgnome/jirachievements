package com.madgnome.jira.plugins.jirachievements.services;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.madgnome.jira.plugins.jirachievements.data.ao.AchievementRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserManager
{
  private final static Logger logger  = LoggerFactory.getLogger(UserManager.class);
  private final JiraAuthenticationContext jiraAuthenticationContext;
  private final IUserWrapperDaoService userWrapperDaoService;
  private final AchievementManager achievementManager;

  public UserManager(JiraAuthenticationContext jiraAuthenticationContext, IUserWrapperDaoService userWrapperDaoService, AchievementManager achievementManager)
  {
    this.jiraAuthenticationContext = jiraAuthenticationContext;
    this.userWrapperDaoService = userWrapperDaoService;
    this.achievementManager = achievementManager;
  }

  public UserWrapper getCurrentUserWrapper()
  {
    User user = jiraAuthenticationContext.getLoggedInUser();

    return get(user);
  }

  public UserWrapper get(String username)
  {
    UserWrapper userWrapper = userWrapperDaoService.get(username);
    if (userWrapper == null)
    {
      logger.info("Create userWrapper for user <{}>", username);
      userWrapper = userWrapperDaoService.getOrCreate(username);
      addFirstAchievements(userWrapper);
    }

    return userWrapper;
  }

  public UserWrapper get(User confluenceUser)
  {
    return get(confluenceUser.getName());
  }

  private void addFirstAchievements(UserWrapper userWrapper)
  {
    // TODO: ugly, this class shouldn't know what achievements to add.
    achievementManager.addAchievementToUser(AchievementRefEnum.WELCOME, userWrapper);
  }
}
