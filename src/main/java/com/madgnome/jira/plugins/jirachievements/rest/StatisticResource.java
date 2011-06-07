package com.madgnome.jira.plugins.jirachievements.rest;

import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.jql.parser.JqlParseException;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.security.PermissionManager;
import com.madgnome.jira.plugins.jirachievements.services.AchievementManager;
import com.madgnome.jira.plugins.jirachievements.services.UserManager;
import com.madgnome.jira.plugins.jirachievements.statistics.IStatisticsCalculator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/statistics")
public class StatisticResource extends AbstractBaseResource
{
  private final IStatisticsCalculator statisticsCalculator;

  public StatisticResource(JiraAuthenticationContext jiraAuthenticationContext, PermissionManager permissionManager, UserManager userManager, AchievementManager achievementManager, IStatisticsCalculator statisticsCalculator)
  {
    super(jiraAuthenticationContext, permissionManager, userManager, achievementManager);
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
