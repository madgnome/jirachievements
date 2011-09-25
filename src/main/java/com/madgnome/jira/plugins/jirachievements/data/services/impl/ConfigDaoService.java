package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.madgnome.jira.plugins.jirachievements.data.ao.Config;
import com.madgnome.jira.plugins.jirachievements.data.ao.ConfigRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.services.IConfigDaoService;

public class ConfigDaoService extends ReferencableDaoService<Config, ConfigRefEnum> implements IConfigDaoService
{
  public ConfigDaoService(ActiveObjects ao)
  {
    super(ao);
  }

  @Override
  protected Class<Config> getClazz()
  {
    return Config.class;
  }

  public Config getOrCreate(ConfigRefEnum ref, String initialValue)
  {
    Config config = get(ref);
    if (config == null)
    {
      config = create(ref);
      config.setValue(initialValue);
      config.save();
    }

    return config;
  }

  public void setValue(String ref, String value)
  {
    Config config = getOrCreate(ref);
    config.setValue(value);
    config.save();
  }
}
