package com.madgnome.jira.plugins.jirachievements.utils.initializers;

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
    statisticRefDaoService.getOrCreate("IssueCount");
    statisticRefDaoService.getOrCreate("CreatedIssueCount");
    statisticRefDaoService.getOrCreate("ResolvedIssueCount");
    statisticRefDaoService.getOrCreate("ClosedIssueCount");
  }
}
