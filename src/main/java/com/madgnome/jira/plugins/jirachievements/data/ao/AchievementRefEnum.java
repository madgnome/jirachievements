package com.madgnome.jira.plugins.jirachievements.data.ao;

public enum AchievementRefEnum
{
  WELCOME("welcome"),
  PADAWAN_USER("padawan:user"),
  PADAWAN_DEVELOPER("padawan:developer"),
  PADAWAN_TESTER("padawan:tester");

  private final String ref;

  AchievementRefEnum(String ref)
  {
    this.ref = ref;
  }

  @Override
  public String toString()
  {
    return ref;
  }
}
