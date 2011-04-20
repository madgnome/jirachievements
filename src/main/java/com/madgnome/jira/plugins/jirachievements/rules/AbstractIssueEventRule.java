package com.madgnome.jira.plugins.jirachievements.rules;

import com.atlassian.jira.security.JiraAuthenticationContext;
import com.madgnome.jira.plugins.jirachievements.data.services.IAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;

public abstract class AbstractIssueEventRule implements IIssueEventRule
{
  protected final JiraAuthenticationContext jiraAuthenticationContext;
  protected final IUserWrapperDaoService userWrapperDaoService;
  protected final IAchievementDaoService achievementDaoService;
  protected final IUserAchievementDaoService userAchievementDaoService;

  public AbstractIssueEventRule(JiraAuthenticationContext jiraAuthenticationContext,
                                IUserWrapperDaoService userWrapperDaoService,
                                IAchievementDaoService achievementDaoService,
                                IUserAchievementDaoService userAchievementDaoService)
  {
    this.jiraAuthenticationContext = jiraAuthenticationContext;
    this.userWrapperDaoService = userWrapperDaoService;
    this.achievementDaoService = achievementDaoService;
    this.userAchievementDaoService = userAchievementDaoService;
  }
}
