package com.madgnome.jira.plugins.jirachievements;

import com.atlassian.jira.extension.Startable;
import com.madgnome.jira.plugins.jirachievements.statistics.IStatisticsCalculator;
import com.madgnome.jira.plugins.jirachievements.utils.initializers.ITableInitializer;

import java.util.List;

public class PluginInitializer implements Startable
{
  private final List<ITableInitializer> tableInitializers;
  private final IStatisticsCalculator statisticCalculator;

  public PluginInitializer(List<ITableInitializer> tableInitializers, IStatisticsCalculator statisticsCalculator)
  {
    this.tableInitializers = tableInitializers;
    this.statisticCalculator = statisticsCalculator;
  }

  @Override
  public void start() throws Exception
  {
    initDatabase();
    statisticCalculator.calculateAll();
  }

  private void initDatabase()
  {
    for (ITableInitializer tableInitializer : tableInitializers)
    {
      tableInitializer.initialize();
    }
  }
}
