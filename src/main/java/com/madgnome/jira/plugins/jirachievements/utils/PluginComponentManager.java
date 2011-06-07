package com.madgnome.jira.plugins.jirachievements.utils;

import com.madgnome.jira.plugins.jirachievements.rules.RulesChecker;
import com.madgnome.jira.plugins.jirachievements.statistics.IStatisticsCalculator;

public class PluginComponentManager
{
  private static RulesChecker rulesChecker;
  private static IStatisticsCalculator statisticsCalculator;

  public PluginComponentManager(RulesChecker rulesChecker, IStatisticsCalculator statisticsCalculator)
  {
    PluginComponentManager.rulesChecker = rulesChecker;
    PluginComponentManager.statisticsCalculator = statisticsCalculator;
  }

  public static RulesChecker getRulesChecker()
  {
    return rulesChecker;
  }

  public static IStatisticsCalculator getStatisticsCalculator()
  {
    return statisticsCalculator;
  }
}
