package com.madgnome.jira.plugins.jirachievements.rest;

import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.jql.parser.JqlParseException;
import com.madgnome.jira.plugins.jirachievements.statistics.IStatisticCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/statistics")
public class StatisticResource
{
  private final Logger logger = LoggerFactory.getLogger(StatisticResource.class);
  private final List<IStatisticCalculator> statisticCalculators;

  public StatisticResource(List<IStatisticCalculator> statisticCalculators)
  {
    this.statisticCalculators = statisticCalculators;
  }

  @GET
  @Path("/calculate")
  public Response calculate()
  {
    try
    {
      for (IStatisticCalculator statisticCalculator : statisticCalculators)
      {
        statisticCalculator.calculate();
      }
    }
    catch (SearchException e)
    {
      logger.error("Couldn't calculate statistics", e);
    } catch (JqlParseException e)
    {
      logger.error("Couldn't calculate statistics", e);
    }

    return Response.ok().build();
  }
}
