package com.madgnome.jira.plugins.jirachievements.utils;

import com.madgnome.jira.plugins.jirachievements.data.services.IConfigDaoService;
import com.madgnome.jira.plugins.jirachievements.rules.RulesChecker;
import com.madgnome.jira.plugins.jirachievements.statistics.IStatisticsCalculator;

public class PluginComponentManager
{
  private static RulesChecker rulesChecker;
  private static IStatisticsCalculator statisticsCalculator;
  private static IConfigDaoService configDaoService;

  public PluginComponentManager(RulesChecker rulesChecker, IStatisticsCalculator statisticsCalculator,
                                IConfigDaoService configDaoService)
  {
    PluginComponentManager.rulesChecker = rulesChecker;
    PluginComponentManager.statisticsCalculator = statisticsCalculator;
    PluginComponentManager.configDaoService = configDaoService;
  }

  public static RulesChecker getRulesChecker()
  {
    return rulesChecker;
  }

  public static IStatisticsCalculator getStatisticsCalculator()
  {
    return statisticsCalculator;
  }

  public static IConfigDaoService getConfigDaoService()
  {
    return configDaoService;
  }
}
