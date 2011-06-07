package com.madgnome.jira.plugins.jirachievements.rest;

import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.security.PermissionManager;
import com.madgnome.jira.plugins.jirachievements.services.AchievementManager;
import com.madgnome.jira.plugins.jirachievements.services.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractBaseResource
{
  protected final Logger logger = LoggerFactory.getLogger(AbstractBaseResource.class);

  protected final JiraAuthenticationContext jiraAuthenticationContext;
  protected final PermissionManager permissionManager;

  protected final UserManager userManager;
  protected final AchievementManager achievementManager;

  public AbstractBaseResource(JiraAuthenticationContext jiraAuthenticationContext, PermissionManager permissionManager, UserManager userManager, AchievementManager achievementManager)
  {
    this.jiraAuthenticationContext = jiraAuthenticationContext;
    this.permissionManager = permissionManager;
    this.userManager = userManager;
    this.achievementManager = achievementManager;
  }
}
