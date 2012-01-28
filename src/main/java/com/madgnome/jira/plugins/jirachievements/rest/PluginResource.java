package com.madgnome.jira.plugins.jirachievements.rest;

import com.madgnome.jira.plugins.jirachievements.rules.RulesChecker;
import com.madgnome.jira.plugins.jirachievements.scheduling.JobsScheduler;
import com.madgnome.jira.plugins.jirachievements.services.LevelManager;
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
  private final JobsScheduler jobsScheduler;
  private final LevelManager levelManager;

  public PluginResource(TableInitializers tableInitializers, IStatisticsCalculator statisticsCalculator, RulesChecker rulesChecker, JobsScheduler jobsScheduler, LevelManager levelManager)
  {
    this.tableInitializers = tableInitializers;
    this.statisticsCalculator = statisticsCalculator;
    this.rulesChecker = rulesChecker;
    this.jobsScheduler = jobsScheduler;
    this.levelManager = levelManager;
  }

  @GET
  @Path("/reset")
  public Response reset()
  {
    jobsScheduler.unscheduleJobs();

    tableInitializers.initialize();
    statisticsCalculator.calculateAll();
    rulesChecker.check();
    levelManager.checkLevelsForAllUser();

    jobsScheduler.scheduleJobs();

    return Response.ok().build();
  }
}
