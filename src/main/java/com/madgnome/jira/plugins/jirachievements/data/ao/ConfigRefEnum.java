package com.madgnome.jira.plugins.jirachievements.data.ao;

public enum ConfigRefEnum
{
  ACHIEVEMENTS_REFRESH_RATE("achievements_refresh_rate"),
  STATISTICS_REFRESH_RATE("statistics_refresh_rate");

  private final String ref;

  ConfigRefEnum(String ref)
  {
    this.ref = ref;
  }

  @Override
  public String toString()
  {
    return ref;
  }
}
