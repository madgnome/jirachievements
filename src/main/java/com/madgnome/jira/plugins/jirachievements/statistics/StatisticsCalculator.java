package com.madgnome.jira.plugins.jirachievements.statistics;

import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.jql.parser.JqlParseException;

import java.util.List;

public class StatisticsCalculator implements IStatisticCalculator
{
  private final List<IStatisticCalculator> statisticCalculators;

  public StatisticsCalculator(List<IStatisticCalculator> statisticCalculators)
  {
    this.statisticCalculators = statisticCalculators;
  }


  @Override
  public void calculate() throws SearchException, JqlParseException
  {
    for (IStatisticCalculator calculator : statisticCalculators)
    {
      calculator.calculate();
    }
  }
}
