package com.madgnome.jira.plugins.jirachievements.data.services;

import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;

import java.util.List;

public interface IUserAchievementsDaoService
{
  List<Achievement> findUserAchievements(int jiraUserId);
}
