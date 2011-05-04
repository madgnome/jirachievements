package com.madgnome.jira.plugins.jirachievements.rules;

import com.atlassian.jira.event.issue.IssueEvent;

public interface IIssueEventRule<T>
{
  void execute(IssueEvent issueEvent);
}
