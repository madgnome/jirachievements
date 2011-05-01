package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.AchievementRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.services.IAchievementDaoService;

public class AchievementDaoService extends ReferencableDaoService<Achievement, AchievementRefEnum> implements IAchievementDaoService
{
  @Override
  protected Class<Achievement> getClazz()
  {
    return Achievement.class;
  }

  public AchievementDaoService(ActiveObjects ao)
  {
    super(ao);
  }
}
