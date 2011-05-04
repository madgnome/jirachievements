package com.madgnome.jira.plugins.jirachievements.utils.initializers;

import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.services.IStatisticRefDaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatisticsRefInitializer implements ITableInitializer
{
  private final static Logger logger = LoggerFactory.getLogger(StatisticsRefInitializer.class);

  private final IStatisticRefDaoService statisticRefDaoService;

  public StatisticsRefInitializer(IStatisticRefDaoService statisticRefDaoService)
  {
    this.statisticRefDaoService = statisticRefDaoService;
  }

  @Override
  public void initialize()
  {
    statisticRefDaoService.getOrCreate(StatisticRefEnum.CREATED_ISSUE_COUNT);
    statisticRefDaoService.getOrCreate(StatisticRefEnum.RESOLVED_ISSUE_COUNT);
    statisticRefDaoService.getOrCreate(StatisticRefEnum.TESTED_ISSUE_COUNT);
  }
}
