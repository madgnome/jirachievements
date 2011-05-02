package com.madgnome.jira.plugins.jirachievements.rest;

import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.jql.parser.JqlParseException;
import com.madgnome.jira.plugins.jirachievements.statistics.IStatisticsCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/statistics")
public class StatisticResource
{
  private final Logger logger = LoggerFactory.getLogger(StatisticResource.class);
  private final IStatisticsCalculator statisticsCalculator;

  public StatisticResource(IStatisticsCalculator statisticsCalculator)
  {
    this.statisticsCalculator = statisticsCalculator;
  }

  @GET
  @Path("/calculate")
  public Response calculate()
  {
    try
    {
      statisticsCalculator.calculateAll();
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
