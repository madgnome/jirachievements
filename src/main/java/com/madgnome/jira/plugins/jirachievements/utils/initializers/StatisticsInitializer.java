package com.madgnome.jira.plugins.jirachievements.utils.initializers;

import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.services.IStatisticRefDaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatisticsInitializer implements ITableInitializer
{
  private final static Logger logger = LoggerFactory.getLogger(StatisticsInitializer.class);

  private final IStatisticRefDaoService statisticRefDaoService;

  public StatisticsInitializer(IStatisticRefDaoService statisticRefDaoService)
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
