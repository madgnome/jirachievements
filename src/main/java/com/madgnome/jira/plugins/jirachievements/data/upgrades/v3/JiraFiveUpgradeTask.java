package com.madgnome.jira.plugins.jirachievements.data.upgrades.v3;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.madgnome.jira.plugins.jirachievements.data.ao.*;
import com.madgnome.jira.plugins.jirachievements.data.upgrades.AbstractUpgradeTask;

public class JiraFiveUpgradeTask extends AbstractUpgradeTask
{
  private final Category[] categories = Category.values();
  private final Difficulty[] difficulties = Difficulty.values();

  @Override
  protected int getVersion()
  {
    return 3;
  }

  @Override
  protected void doUpgrade(ActiveObjects ao)
  {
    ao.migrate(Achievement.class,
              com.madgnome.jira.plugins.jirachievements.data.ao.Achievement.class,
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

    upgradeLevels(ao);
    upgradeAchievements(ao);
  }

  private void upgradeAchievements(ActiveObjects ao)
  {
    for (Achievement achievement : ao.find(Achievement.class))
    {
      if (upgradeAchievementCategory(achievement) ||
          upgradeAchievementDifficulty(achievement))
      {
        achievement.save();
      }
    }
  }

  private boolean upgradeAchievementCategory(Achievement achievement)
  {
    String category = achievement.getCategory();
    int index = -1;
    try
    {
      index = Integer.parseInt(category);
    }
    catch (NumberFormatException e)
    {
      //
    }

    if (index != -1)
    {
      achievement.setCategory(categories[index].name());
      return true;
    }

    return false;
  }

  private boolean upgradeAchievementDifficulty(Achievement achievement)
  {
    String difficulty = achievement.getDifficulty();
    int index = -1;
    try
    {
      index = Integer.parseInt(difficulty);
    }
    catch (NumberFormatException e)
    {
      //
    }

    if (index != -1)
    {
      achievement.setDifficulty(difficulties[index].name());
      return true;
    }

    return false;
  }

  private void upgradeLevels(ActiveObjects ao)
  {
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
