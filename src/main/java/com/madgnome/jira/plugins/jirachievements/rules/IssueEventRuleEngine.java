package com.madgnome.jira.plugins.jirachievements.rules;

import com.atlassian.jira.ComponentManager;
import com.atlassian.jira.event.issue.IssueEvent;

import java.util.ArrayList;
import java.util.List;

public class IssueEventRuleEngine implements IRuleEngine<IssueEvent>
{
  private final List<IIssueEventRule> rules;

  public IssueEventRuleEngine(List<IIssueEventRule> rules)
  {
    this.rules = rules;
  }

  public void execute(IssueEvent issueEvent)
  {
    for (IIssueEventRule rule : rules)
    {
      rule.execute(issueEvent);
    }
  }

  private List<IIssueEventRule> registerIssueEventRules()
  {
    List<IIssueEventRule> rules = new ArrayList<IIssueEventRule>();
    rules.add(ComponentManager.getComponent(PadawanIssueEventRule.class));

    return rules;
  }
}
