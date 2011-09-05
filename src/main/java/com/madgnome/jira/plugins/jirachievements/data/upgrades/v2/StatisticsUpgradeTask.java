package com.madgnome.jira.plugins.jirachievements.data.upgrades.v2;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.madgnome.jira.plugins.jirachievements.data.upgrades.AbstractUpgradeTask;
import com.madgnome.jira.plugins.jirachievements.data.utils.KeyableUtils;

public class StatisticsUpgradeTask extends AbstractUpgradeTask
{
  @Override
  protected int getVersion()
  {
    return 2;
  }

  @Override
  @SuppressWarnings({"unchecked"})
  protected void doUpgrade(ActiveObjects ao)
  {
    ao.migrate(ComponentStatistic.class,
               ProjectStatistic.class,
               UserStatistic.class,
               VersionStatistic.class);

    for (UserStatistic statistic : ao.find(UserStatistic.class))
    {
      statistic.setKey(KeyableUtils.buildKey(statistic));
      statistic.save();
    }

    for (ProjectStatistic statistic : ao.find(ProjectStatistic.class))
    {
      statistic.setKey(KeyableUtils.buildKey(statistic));
      statistic.save();
    }

    for (ComponentStatistic statistic : ao.find(ComponentStatistic.class))
    {
      statistic.setKey(KeyableUtils.buildKey(statistic));
      statistic.save();
    }

    for (VersionStatistic statistic : ao.find(VersionStatistic.class))
    {
      statistic.setKey(KeyableUtils.buildKey(statistic));
      statistic.save();
    }
  }
}
