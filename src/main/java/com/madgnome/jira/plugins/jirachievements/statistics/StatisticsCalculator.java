package com.madgnome.jira.plugins.jirachievements.statistics;

import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.jql.parser.JqlParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class StatisticsCalculator implements IStatisticsCalculator
{
  private final static Logger logger = LoggerFactory.getLogger(StatisticsCalculator.class);
  private final List<IStatisticCalculator> statisticCalculators;

  public StatisticsCalculator(List<IStatisticCalculator> statisticCalculators)
  {
    this.statisticCalculators = statisticCalculators;
  }


  @Override
  public void calculateAll()
  {
    for (IStatisticCalculator calculator : statisticCalculators)
    {
      try
      {
        calculator.calculate();
      }
      catch (SearchException e)
      {
        logger.error("Failed to calculate statistic <{}>", calculator.getClass().getName(), e);
      }
      catch (JqlParseException e)
      {
        logger.error("Failed to calculate statistic <{}>", calculator.getClass().getName(), e);
      }
    }
  }
}
