package com.madgnome.jira.plugins.jirachievements.rules;

import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.madgnome.jira.plugins.jirachievements.data.services.IAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;

public class PadawanIssueEventRule extends AbstractRule implements IIssueEventRule<IssueEvent>
{
  private final PadawanRule padawanRule;

  public PadawanIssueEventRule(JiraAuthenticationContext jiraAuthenticationContext, IUserWrapperDaoService userWrapperDaoService, IAchievementDaoService achievementDaoService, IUserAchievementDaoService userAchievementDaoService, PadawanRule padawanRule)
  {
    super(jiraAuthenticationContext, userWrapperDaoService, achievementDaoService, userAchievementDaoService);
    this.padawanRule = padawanRule;
  }

  @Override
  public void execute(IssueEvent issueEvent)
  {
    Long eventTypeId = issueEvent.getEventTypeId();
    padawanRule.check(eventTypeId);
  }


}
