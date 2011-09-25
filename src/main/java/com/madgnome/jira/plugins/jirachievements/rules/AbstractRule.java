package com.madgnome.jira.plugins.jirachievements.rules;

import com.atlassian.jira.security.JiraAuthenticationContext;
import com.madgnome.jira.plugins.jirachievements.data.services.IAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;
import com.madgnome.jira.plugins.jirachievements.services.WorkflowConfiguration;

public abstract class AbstractRule
{
  protected final JiraAuthenticationContext jiraAuthenticationContext;
  protected final IUserWrapperDaoService userWrapperDaoService;
  protected final IAchievementDaoService achievementDaoService;
  protected final IUserAchievementDaoService userAchievementDaoService;
  protected final WorkflowConfiguration workflowConfiguration;

  public AbstractRule(JiraAuthenticationContext jiraAuthenticationContext,
                      IUserWrapperDaoService userWrapperDaoService,
                      IAchievementDaoService achievementDaoService,
                      IUserAchievementDaoService userAchievementDaoService,
                      WorkflowConfiguration workflowConfiguration)
  {
    this.jiraAuthenticationContext = jiraAuthenticationContext;
    this.userWrapperDaoService = userWrapperDaoService;
    this.achievementDaoService = achievementDaoService;
    this.userAchievementDaoService = userAchievementDaoService;
    this.workflowConfiguration = workflowConfiguration;
  }
}
