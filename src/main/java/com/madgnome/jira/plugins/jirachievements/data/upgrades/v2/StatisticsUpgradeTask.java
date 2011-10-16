package com.madgnome.jira.plugins.jirachievements.data.upgrades.v2;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.config.ConstantsManager;
import com.atlassian.jira.issue.status.Status;
import com.madgnome.jira.plugins.jirachievements.data.ao.*;
import com.madgnome.jira.plugins.jirachievements.data.services.IConfigDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.impl.ConfigDaoService;
import com.madgnome.jira.plugins.jirachievements.data.upgrades.AbstractUpgradeTask;
import com.madgnome.jira.plugins.jirachievements.data.utils.KeyableUtils;

public class StatisticsUpgradeTask extends AbstractUpgradeTask
{
  private IConfigDaoService configDaoService;
  private final ConstantsManager constantsManager;

  public StatisticsUpgradeTask(ConstantsManager constantsManager)
  {
    this.constantsManager = constantsManager;
  }

  @Override
  protected int getVersion()
  {
    return 2;
  }

  @Override
  @SuppressWarnings({"unchecked"})
  protected void doUpgrade(ActiveObjects ao)
  {
    ao.migrate(Achievement.class,
               ComponentStatistic.class,
               Config.class,
               Level.class,
               ProjectStatistic.class,
               Statistic.class,
               StatisticRef.class,
               UserAchievement.class,
               UserStatistic.class,
               UserWrapper.class,
               VersionStatistic.class);

    configDaoService = new ConfigDaoService(ao);

    upgradeConfig();
    upgradeStatistics(ao);
  }

  private void upgradeConfig()
  {
    createConfigForStatus(ConfigRefEnum.OPEN_WORKFLOW_STATUSES, "Open");
    createConfigForStatus(ConfigRefEnum.RESOLVED_WORKFLOW_STATUSES, "Resolved");
    createConfigForStatus(ConfigRefEnum.REOPENED_WORKFLOW_STATUSES, "Reopened");
    createConfigForStatus(ConfigRefEnum.CLOSED_WORKFLOW_STATUSES, "Closed");
  }

  private void createConfigForStatus(ConfigRefEnum configRef, String statusName)
  {
    Status status = constantsManager.getStatusByName(statusName);
    configDaoService.getOrCreate(configRef, status != null ? status.getId() : "");
  }

  private void upgradeStatistics(ActiveObjects ao)
  {
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
