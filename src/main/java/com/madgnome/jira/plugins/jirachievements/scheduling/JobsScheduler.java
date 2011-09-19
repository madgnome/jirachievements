package com.madgnome.jira.plugins.jirachievements.scheduling;

import com.atlassian.sal.api.scheduling.PluginScheduler;

import java.util.Calendar;
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
      scheduleJob(job);
    }
  }

  public void unscheduleJobs()
  {
    for (IJob job : jobs)
    {
      unscheduleJob(job);
    }
  }

  public void resetJobs()
  {
    for (IJob job : jobs)
    {
      unscheduleJob(job);
      scheduleJob(job);
    }
  }

  public void resetJob(String jobName)
  {
    for (IJob job : jobs)
    {
      if (job.getName().equals(jobName))
      {
        unscheduleJob(job);
        scheduleJob(job);
        break;
      }
    }
  }

  private void scheduleJob(IJob job)
  {
    try
    {
      final long repeatIntervalInSeconds = job.getRepeatIntervalInSeconds();
      pluginScheduler.scheduleJob(job.getName(), job.getClass(), null, getExecutionDate(repeatIntervalInSeconds), repeatIntervalInSeconds *MILLISECONDS_IN_SECOND);
    }
    catch (IllegalArgumentException e)
    {
      // Ignore exception if job is not schedule
    }

  }

  private void unscheduleJob(IJob job)
  {
    pluginScheduler.unscheduleJob(job.getName());
  }

  private Date getExecutionDate(long repeatIntervalInSeconds)
  {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.SECOND, (int) repeatIntervalInSeconds);

    return calendar.getTime();
  }
}
