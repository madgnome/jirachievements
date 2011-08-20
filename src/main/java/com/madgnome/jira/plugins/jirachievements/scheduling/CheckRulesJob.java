package com.madgnome.jira.plugins.jirachievements.scheduling;

import com.madgnome.jira.plugins.jirachievements.data.ao.ConfigRefEnum;
import com.madgnome.jira.plugins.jirachievements.rules.RulesChecker;
import com.madgnome.jira.plugins.jirachievements.utils.PluginComponentManager;

import java.util.Map;

public class CheckRulesJob extends AbstractJob
{
  private final static String JOB_NAME = "CheckRules";
  private RulesChecker rulesChecker;

  public void setRulesChecker(RulesChecker rulesChecker)
  {
    this.rulesChecker = rulesChecker;
  }

  @Override
  protected ConfigRefEnum getRefreshRateConfigRefEnum()
  {
    return ConfigRefEnum.ACHIEVEMENTS_REFRESH_RATE;
  }

  @Override
  public void execute(Map<String, Object> stringObjectMap)
  {
    if (rulesChecker == null)
    {
      rulesChecker = PluginComponentManager.getRulesChecker();
    }

    rulesChecker.check();
  }

  @Override
  public String getName()
  {
    return JOB_NAME;
  }
}
