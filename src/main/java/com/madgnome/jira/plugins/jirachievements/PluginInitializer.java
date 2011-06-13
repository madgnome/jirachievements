package com.madgnome.jira.plugins.jirachievements;

import com.atlassian.jira.extension.Startable;
import com.madgnome.jira.plugins.jirachievements.scheduling.JobsScheduler;

public class PluginInitializer implements Startable
{
  private final JobsScheduler jobsScheduler;

  public PluginInitializer(JobsScheduler jobsScheduler)
  {
    this.jobsScheduler = jobsScheduler;
  }

  @Override
  public void start() throws Exception
  {
    initJobs();
  }

  private void initJobs()
  {
    jobsScheduler.scheduleJobs();
  }
}
