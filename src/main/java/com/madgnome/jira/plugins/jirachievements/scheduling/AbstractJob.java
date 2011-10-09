package com.madgnome.jira.plugins.jirachievements.scheduling;

import com.madgnome.jira.plugins.jirachievements.data.ao.Config;
import com.madgnome.jira.plugins.jirachievements.data.ao.ConfigRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.services.IConfigDaoService;
import com.madgnome.jira.plugins.jirachievements.utils.PluginComponentManager;

public abstract class AbstractJob implements IJob
{
  private final static int DEFAULT_REFRESH_RATE = 60;

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
      final Config refreshRateConfig = configDaoService.get(getRefreshRateConfigRefEnum());
      refreshRateInMinute = refreshRateConfig != null ? Integer.parseInt(refreshRateConfig.getValue()) : DEFAULT_REFRESH_RATE;
    }

    return refreshRateInMinute * 60L;
  }
}
