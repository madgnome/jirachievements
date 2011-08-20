package com.madgnome.jira.plugins.jirachievements.scheduling;

import com.atlassian.sal.api.scheduling.PluginScheduler;

import java.util.Date;
import java.util.Set;

public class  JobsScheduler
{
  private final static long MILLISECONDS_IN_SECOND = 1000l;
  private final PluginScheduler pluginScheduler;
  private final Set<IJob> jobs;

  public JobsScheduler(PluginScheduler pluginScheduler, Set<IJob> jobs)
  {
    this.pluginScheduler = pluginScheduler;
    this.jobs = jobs;
  }

  public void scheduleJobs()
  {
    for (IJob job : jobs)
    {
      pluginScheduler.scheduleJob(job.getName(), job.getClass(), null, new Date(), job.getRepeatIntervalInSeconds()*MILLISECONDS_IN_SECOND);
    }
  }

  public void resetJobs()
  {
    for (IJob job : jobs)
    {
      pluginScheduler.unscheduleJob(job.getName());
      pluginScheduler.scheduleJob(job.getName(), job.getClass(), null, new Date(), job.getRepeatIntervalInSeconds()*MILLISECONDS_IN_SECOND);
    }
  }

  public void resetJob(String jobName)
  {
    for (IJob job : jobs)
    {
      if (job.getName().equals(jobName))
      {
        pluginScheduler.unscheduleJob(job.getName());
        pluginScheduler.scheduleJob(job.getName(), job.getClass(), null, new Date(), job.getRepeatIntervalInSeconds()*MILLISECONDS_IN_SECOND);
        break;
      }
    }
  }
}
