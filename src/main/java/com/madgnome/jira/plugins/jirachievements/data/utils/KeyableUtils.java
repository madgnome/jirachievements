package com.madgnome.jira.plugins.jirachievements.data.utils;

import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRef;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.upgrades.v2.ComponentStatistic;
import com.madgnome.jira.plugins.jirachievements.data.upgrades.v2.ProjectStatistic;
import com.madgnome.jira.plugins.jirachievements.data.upgrades.v2.UserStatistic;
import com.madgnome.jira.plugins.jirachievements.data.upgrades.v2.VersionStatistic;
import net.java.ao.Entity;

public class KeyableUtils
{
  private final static String SEPARATOR = "|";

  public static String buildKey(UserStatistic statistic)
  {
    return buildKey(statistic.getStatisticRef(), statistic.getUserWrapper());
  }

  public static String buildKey(String a, String b)
  {
    return a + SEPARATOR + b;
  }
  
  public static String buildKey(Entity a, Entity b)
  {
    return a.getID() + SEPARATOR + b.getID();
  }

  public static String buildKey(StatisticRef statisticRef, UserWrapper userWrapper)
  {
    return statisticRef.getID() + SEPARATOR  + userWrapper.getID();
  }

  public static String buildKey(ProjectStatistic statistic)
  {
    return buildKey(statistic.getProjectKey(), statistic.getStatisticRef(), statistic.getUserWrapper());
  }

  public static String buildKey(String projectKey, StatisticRef statisticRef, UserWrapper userWrapper)
  {
    return statisticRef.getID() + SEPARATOR + projectKey + SEPARATOR + userWrapper.getID();
  }

  public static String buildKey(ComponentStatistic statistic)
  {
    return buildKey(statistic.getProjectKey(), statistic.getComponent(), statistic.getStatisticRef(), statistic.getUserWrapper());
  }

  public static String buildKey(VersionStatistic statistic)
  {
    return buildKey(statistic.getProjectKey(), statistic.getVersion(), statistic.getStatisticRef(), statistic.getUserWrapper());
  }

  public static String buildKey(String projectKey, String versionOrComponent, StatisticRef statisticRef, UserWrapper userWrapper)
  {
    return statisticRef.getID() + SEPARATOR  + projectKey + SEPARATOR + versionOrComponent + SEPARATOR + userWrapper.getID();
  }
}
