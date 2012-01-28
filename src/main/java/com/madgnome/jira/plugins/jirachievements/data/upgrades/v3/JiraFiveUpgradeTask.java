package com.madgnome.jira.plugins.jirachievements.data.upgrades.v3;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.madgnome.jira.plugins.jirachievements.data.ao.*;
import com.madgnome.jira.plugins.jirachievements.data.upgrades.AbstractUpgradeTask;

public class JiraFiveUpgradeTask extends AbstractUpgradeTask
{
  @Override
  protected int getVersion()
  {
    return 3;
  }

  @Override
  protected void doUpgrade(ActiveObjects ao)
  {
    ao.migrate(Achievement.class,
              ComponentStatistic.class,
              Config.class,
              Level.class,
              com.madgnome.jira.plugins.jirachievements.data.ao.Level.class,
              ProjectStatistic.class,
              Statistic.class,
              StatisticRef.class,
              UserAchievement.class,
              UserStatistic.class,
              UserWrapper.class,
              VersionStatistic.class);

    for (Level level : ao.find(Level.class))
    {
      boolean changed = false;
      if (level.getNumber() != 0)
      {
        level.setLevelNumber(level.getNumber());
        changed = true;
      }

      if (changed)
      {
        level.save();
      }
    }
  }
}
