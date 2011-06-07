package com.madgnome.jira.plugins.jirachievements.scheduling;

import com.atlassian.sal.api.scheduling.PluginJob;

public interface IJob extends PluginJob
{
  String getName();
  long getRepeatIntervalInSeconds();
}
