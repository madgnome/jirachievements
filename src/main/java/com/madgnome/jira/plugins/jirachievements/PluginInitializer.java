package com.madgnome.jira.plugins.jirachievements;

import com.atlassian.jira.extension.Startable;
import com.madgnome.jira.plugins.jirachievements.statistics.IStatisticCalculator;
import com.madgnome.jira.plugins.jirachievements.utils.initializers.ITableInitializer;

import java.util.List;

public class PluginInitializer implements Startable
{
  private final List<ITableInitializer> tableInitializers;
  private final IStatisticCalculator statisticCalculator;

  public PluginInitializer(List<ITableInitializer> tableInitializers, IStatisticCalculator statisticCalculator)
  {
    this.tableInitializers = tableInitializers;
    this.statisticCalculator = statisticCalculator;
  }

  @Override
  public void start() throws Exception
  {
    initDatabase();
    statisticCalculator.calculate();
  }

  private void initDatabase()
  {
    for (ITableInitializer tableInitializer : tableInitializers)
    {
      tableInitializer.initialize();
    }
  }
}
