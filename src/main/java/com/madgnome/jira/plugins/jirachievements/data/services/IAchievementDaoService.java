package com.madgnome.jira.plugins.jirachievements.data.services;

import com.atlassian.activeobjects.tx.Transactional;
import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;

import java.util.List;

@Transactional
public interface IAchievementDaoService
{
  Achievement create(String name);
  List<Achievement> all();
}
