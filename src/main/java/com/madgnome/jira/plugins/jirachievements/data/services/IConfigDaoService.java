package com.madgnome.jira.plugins.jirachievements.data.services;

import com.madgnome.jira.plugins.jirachievements.data.ao.Config;
import com.madgnome.jira.plugins.jirachievements.data.ao.ConfigRefEnum;

public interface IConfigDaoService extends IReferencableDaoService<Config, ConfigRefEnum>
{
  Config getOrCreate(ConfigRefEnum ref, String initialValue);
  void setValue(String ref, String value);
  void setValue(ConfigRefEnum ref, String value);
}
