package com.madgnome.jira.plugins.jirachievements.rest;

import com.madgnome.jira.plugins.jirachievements.rules.RulesChecker;
import com.madgnome.jira.plugins.jirachievements.statistics.IStatisticsCalculator;
import com.madgnome.jira.plugins.jirachievements.utils.initializers.TableInitializers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/plugin")
public class PluginResource
{
  private final TableInitializers tableInitializers;
  private final IStatisticsCalculator statisticsCalculator;
  private final RulesChecker rulesChecker;

  public PluginResource(TableInitializers tableInitializers, IStatisticsCalculator statisticsCalculator, RulesChecker rulesChecker)
  {
    this.tableInitializers = tableInitializers;
    this.statisticsCalculator = statisticsCalculator;
    this.rulesChecker = rulesChecker;
  }

  @GET
  @Path("/reset")
  public Response reset()
  {
    tableInitializers.initialize();
    statisticsCalculator.calculateAll();
    rulesChecker.check();

    return Response.ok().build();
  }
}
