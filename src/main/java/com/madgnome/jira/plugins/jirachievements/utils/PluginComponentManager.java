package com.madgnome.jira.plugins.jirachievements.utils;

import com.madgnome.jira.plugins.jirachievements.rules.RulesChecker;

public class PluginComponentManager
{
  private static RulesChecker rulesChecker;

  public PluginComponentManager(RulesChecker rulesChecker)
  {
    PluginComponentManager.rulesChecker = rulesChecker;
  }

  public static RulesChecker getRulesChecker()
  {
    return rulesChecker;
  }
}
