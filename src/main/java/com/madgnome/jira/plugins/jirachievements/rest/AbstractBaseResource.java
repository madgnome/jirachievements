package com.madgnome.jira.plugins.jirachievements.rest;

import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.security.PermissionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractBaseResource
{
  protected final Logger logger = LoggerFactory.getLogger(AbstractBaseResource.class);

  protected final JiraAuthenticationContext jiraAuthenticationContext;
  protected final PermissionManager permissionManager;

  public AbstractBaseResource(JiraAuthenticationContext jiraAuthenticationContext, PermissionManager permissionManager)
  {
    this.jiraAuthenticationContext = jiraAuthenticationContext;
    this.permissionManager = permissionManager;
  }
}
