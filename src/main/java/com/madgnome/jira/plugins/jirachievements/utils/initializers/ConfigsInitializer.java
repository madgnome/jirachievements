package com.madgnome.jira.plugins.jirachievements.utils.initializers;

import com.madgnome.jira.plugins.jirachievements.data.ao.ConfigRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.services.IConfigDaoService;

public class ConfigsInitializer implements ITableInitializer
{
  private final IConfigDaoService configDaoService;

  public ConfigsInitializer(IConfigDaoService configDaoService)
  {
    this.configDaoService = configDaoService;
  }

  @Override
  public void initialize()
  {
    configDaoService.getOrCreate(ConfigRefEnum.ACHIEVEMENTS_REFRESH_RATE, "60");
    configDaoService.getOrCreate(ConfigRefEnum.STATISTICS_REFRESH_RATE, Integer.toString(24*60));
  }
}
