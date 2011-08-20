package com.madgnome.jira.plugins.jirachievements.scheduling;

import com.madgnome.jira.plugins.jirachievements.data.ao.ConfigRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.services.IConfigDaoService;
import com.madgnome.jira.plugins.jirachievements.utils.PluginComponentManager;

public abstract class AbstractJob implements IJob
{
  protected IConfigDaoService configDaoService;

  protected abstract ConfigRefEnum getRefreshRateConfigRefEnum();

  @Override
  public long getRepeatIntervalInSeconds()
  {
    if (configDaoService == null)
    {
      configDaoService = PluginComponentManager.getConfigDaoService();
    }

    int refreshRateInMinute  = 360;
    if (configDaoService != null)
    {
      refreshRateInMinute = Integer.parseInt(configDaoService.get(getRefreshRateConfigRefEnum()).getValue());
    }

    return refreshRateInMinute * 60L;
  }
}
