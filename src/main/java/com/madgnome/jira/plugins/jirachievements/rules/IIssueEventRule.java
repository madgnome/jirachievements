package com.madgnome.jira.plugins.jirachievements.rules;

import com.atlassian.jira.event.issue.IssueEvent;

public interface IIssueEventRule extends IRule
{
  void execute(IssueEvent issueEvent);
}
