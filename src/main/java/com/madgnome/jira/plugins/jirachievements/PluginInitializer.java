package com.madgnome.jira.plugins.jirachievements;

import com.atlassian.sal.api.lifecycle.LifecycleAware;
import com.madgnome.jira.plugins.jirachievements.scheduling.JobsScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

public class PluginInitializer implements LifecycleAware
{
  private static final Logger logger = LoggerFactory.getLogger(PluginInitializer.class);

  private final JobsScheduler jobsScheduler;

  private AtomicBoolean initialized = new AtomicBoolean(false);

  public PluginInitializer(JobsScheduler jobsScheduler)
  {
    this.jobsScheduler = jobsScheduler;
  }

  public void start()
  {
    if (initialized.compareAndSet(false, true))
    {
      initJobs();
    }
  }

  private void initJobs()
  {
    jobsScheduler.scheduleJobs();
  }

  @Override
  public void onStart()
  {
    start();
  }
}
