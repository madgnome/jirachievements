package com.madgnome.jira.plugins.jirachievements.data;

import com.madgnome.jira.plugins.jirachievements.data.ao.*;
import net.java.ao.EntityManager;
import net.java.ao.test.jdbc.DatabaseUpdater;

public class DatabaseProcessor implements DatabaseUpdater
{
  @Override
  @SuppressWarnings("unchecked")
  public void update(EntityManager entityManager) throws Exception
  {
    entityManager.migrate(
            Achievement.class,
            ComponentStatistic.class,
            Config.class,
            Level.class,
            ProjectStatistic.class,
            Statistic.class,
            StatisticRef.class,
            UserAchievement.class,
            UserLevel.class,
            UserStatistic.class,
            UserWrapper.class,
            VersionStatistic.class);
  }
}
