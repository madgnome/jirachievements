package com.madgnome.jira.plugins.jirachievements.scheduling;

import com.madgnome.jira.plugins.jirachievements.data.ao.ConfigRefEnum;
import com.madgnome.jira.plugins.jirachievements.statistics.IStatisticsCalculator;
import com.madgnome.jira.plugins.jirachievements.utils.PluginComponentManager;

import java.util.Map;

public class CalculateStatisticsJob extends AbstractJob
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
  protected ConfigRefEnum getRefreshRateConfigRefEnum()
  {
    return ConfigRefEnum.STATISTICS_REFRESH_RATE;
  }
}
