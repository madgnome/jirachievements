package com.madgnome.jira.plugins.jirachievements;

import com.atlassian.jira.extension.Startable;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.jql.parser.JqlParseException;
import com.madgnome.jira.plugins.jirachievements.rules.RulesChecker;
import com.madgnome.jira.plugins.jirachievements.statistics.IStatisticsCalculator;
import com.madgnome.jira.plugins.jirachievements.utils.initializers.ITableInitializer;

import java.util.List;

public class PluginInitializer implements Startable
{
  private final List<ITableInitializer> tableInitializers;
  private final IStatisticsCalculator statisticCalculator;
  private final RulesChecker rulesChecker;

  public PluginInitializer(List<ITableInitializer> tableInitializers, IStatisticsCalculator statisticsCalculator, RulesChecker rulesChecker)
  {
    this.tableInitializers = tableInitializers;
    this.statisticCalculator = statisticsCalculator;
    this.rulesChecker = rulesChecker;
  }

  @Override
  public void start() throws Exception
  {
    initDatabase();
    initStatisticsValues();
    initAchievements();
  }

  private void initStatisticsValues() throws SearchException, JqlParseException
  {
    statisticCalculator.calculateAll();
  }

  private void initAchievements()
  {
    rulesChecker.check();
  }

  private void initDatabase()
  {
    for (ITableInitializer tableInitializer : tableInitializers)
    {
      tableInitializer.initialize();
    }
  }
}
