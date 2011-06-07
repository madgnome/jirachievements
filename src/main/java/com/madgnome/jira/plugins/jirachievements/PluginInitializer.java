package com.madgnome.jira.plugins.jirachievements;

import com.atlassian.jira.extension.Startable;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.jql.parser.JqlParseException;
import com.madgnome.jira.plugins.jirachievements.scheduling.JobsScheduler;
import com.madgnome.jira.plugins.jirachievements.statistics.IStatisticsCalculator;
import com.madgnome.jira.plugins.jirachievements.utils.initializers.ITableInitializer;

import java.util.List;

public class PluginInitializer implements Startable
{
  private final List<ITableInitializer> tableInitializers;
  private final IStatisticsCalculator statisticCalculator;
  private final JobsScheduler jobsScheduler;

  public PluginInitializer(List<ITableInitializer> tableInitializers, IStatisticsCalculator statisticsCalculator, JobsScheduler jobsScheduler)
  {
    this.tableInitializers = tableInitializers;
    this.statisticCalculator = statisticsCalculator;
    this.jobsScheduler = jobsScheduler;
  }

  @Override
  public void start() throws Exception
  {
//    initDatabase();
//    initStatisticsValues();

    initJobs();
  }

  private void initJobs()
  {
    jobsScheduler.scheduleJobs();
  }

  private void initStatisticsValues() throws SearchException, JqlParseException
  {
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
