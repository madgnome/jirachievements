package com.madgnome.jira.plugins.jirachievements.data.ao;

public enum AchievementRefEnum
{
  WELCOME("welcome"),
  PADAWAN_USER("padawan:user"),
  PADAWAN_DEVELOPER("padawan:developer"),
  PADAWAN_TESTER("padawan:tester"),
  LOST_ARK_RAIDER("lostark-raider"),
  ISSUE_SNATCHER("issue-snatcher"),
  INSOMNIAC_USER("insomniac:user"),
  INSOMNIAC_DEVELOPER("insomniac:developer"),
  INSOMNIAC_TESTER("insomniac:tester"),
  EXPRESSO_USER("expresso:user"),
  EXPRESSO_DEVELOPER("expresso:developer"),
  EXPRESSO_TESTER("expresso:tester");

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
