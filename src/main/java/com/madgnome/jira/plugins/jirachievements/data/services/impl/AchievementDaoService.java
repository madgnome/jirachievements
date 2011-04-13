package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.services.IAchievementDaoService;

import java.util.Arrays;
import java.util.List;

public class AchievementDaoService extends BaseDaoService implements IAchievementDaoService
{

  public AchievementDaoService(ActiveObjects ao)
  {
    super(ao);
  }

  @Override
  public Achievement create(String name)
  {
    Achievement achievement = ao.create(Achievement.class);
    achievement.setName(name);

    return achievement;
  }

  @Override
  public List<Achievement> all()
  {
    return Arrays.asList(ao.find(Achievement.class));
  }
}
