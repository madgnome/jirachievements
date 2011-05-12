package com.madgnome.jira.plugins.jirachievements.data.ao;

public class AchievementImpl
{
  private Achievement achievement;

  public AchievementImpl(Achievement achievement)
  {
    this.achievement = achievement;
  }

  public String getImageRef()
  {
    String ref = achievement.getRef();
    return !ref.contains(":") ? ref : ref.substring(0, ref.indexOf(":"));
  }
}
