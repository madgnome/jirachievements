package com.madgnome.jira.plugins.jirachievements.data.ao;

public enum Category
{
  GENERAL("general"),
  USER("user"),
  DEVELOPER("developer"),
  TESTER("tester"),
  AGILIST("agilist");

  private final String ref;

  Category(String ref)
  {
    this.ref = ref;
  }

  @Override
  public String toString()
  {
    return ref;
  }
}
