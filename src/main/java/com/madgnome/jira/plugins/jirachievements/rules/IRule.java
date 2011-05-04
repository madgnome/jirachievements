package com.madgnome.jira.plugins.jirachievements.rules;

import com.madgnome.jira.plugins.jirachievements.data.ao.AchievementRefEnum;

public interface IRule
{
  AchievementRefEnum getAchievementRef();
  void check();
}
