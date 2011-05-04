package com.madgnome.jira.plugins.jirachievements.rules;

import java.util.Set;

public class RulesChecker
{
  private final Set<IRule> rules;

  public RulesChecker(Set<IRule> rules)
  {
    this.rules = rules;
  }

  public void check()
  {
    for (IRule rule : rules)
    {
      rule.check();
    }
  }
}
