package com.madgnome.jira.plugins.jirachievements.scheduling;

import com.madgnome.jira.plugins.jirachievements.statistics.IStatisticsCalculator;
import com.madgnome.jira.plugins.jirachievements.utils.PluginComponentManager;

import java.util.Map;

public class CalculateStatisticsJob implements IJob
{
  private final static String JOB_NAME = "StatisticCalculator";
  private IStatisticsCalculator statisticsCalculator;

  @Override
  public void execute(Map<String, Object> stringObjectMap)
  {
    if (statisticsCalculator == null)
    {
      statisticsCalculator = PluginComponentManager.getStatisticsCalculator();
    }

    statisticsCalculator.calculateAll();
  }

  @Override
  public String getName()
  {
    return JOB_NAME;
  }

  @Override
  public long getRepeatIntervalInSeconds()
  {
    return 3600l;
  }
}
