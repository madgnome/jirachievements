package com.madgnome.jira.plugins.jirachievements.data.upgrades.v1;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.madgnome.jira.plugins.jirachievements.data.ao.*;
import com.madgnome.jira.plugins.jirachievements.data.services.IAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IConfigDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.ILevelDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IStatisticRefDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.impl.AchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.impl.ConfigDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.impl.LevelDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.impl.StatisticRefDaoService;
import com.madgnome.jira.plugins.jirachievements.data.upgrades.AbstractUpgradeTask;
import com.madgnome.jira.plugins.jirachievements.utils.initializers.AchievementsInitializer;
import com.madgnome.jira.plugins.jirachievements.utils.initializers.ConfigsInitializer;
import com.madgnome.jira.plugins.jirachievements.utils.initializers.LevelsInitializer;
import com.madgnome.jira.plugins.jirachievements.utils.initializers.StatisticsRefInitializer;

public class InitDatabaseTask extends AbstractUpgradeTask
{
  private IAchievementDaoService achievementDaoService;
  private IStatisticRefDaoService statisticRefDaoService;
  private ILevelDaoService levelDaoService;
  private IConfigDaoService configDaoService;

  @Override
  protected int getVersion()
  {
    return 1;
  }

  @Override
  protected void doUpgrade(ActiveObjects ao)
  {
    try
    {
      initializeAO(ao);

      initializeAchievements();
      initializeStatistics();
      initializeConfigs();
      initializeLevels();
    }
    catch (Exception e)
    {
      logger.error("Failed to initialize JIRA Hero database", e);
    }
  }



  @SuppressWarnings({"unchecked"})
  private void initializeAO(ActiveObjects ao)
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

    achievementDaoService = new AchievementDaoService(ao);
    statisticRefDaoService = new StatisticRefDaoService(ao);
    configDaoService = new ConfigDaoService(ao);
    levelDaoService = new LevelDaoService(ao, statisticRefDaoService);
  }

  private void initializeAchievements()
  {
    AchievementsInitializer achievementsInitializerinitializer = new AchievementsInitializer(achievementDaoService);
    achievementsInitializerinitializer.initialize();
  }

  private void initializeStatistics()
  {
    StatisticsRefInitializer statisticsRefInitializer = new StatisticsRefInitializer(statisticRefDaoService);
    statisticsRefInitializer.initialize();
  }

  private void initializeLevels()
  {
    LevelsInitializer levelsInitializer = new LevelsInitializer(levelDaoService);
    levelsInitializer.initialize();
  }

  private void initializeConfigs()
  {
    ConfigsInitializer configsInitializer = new ConfigsInitializer(configDaoService);
    configsInitializer.initialize();
  }
}
