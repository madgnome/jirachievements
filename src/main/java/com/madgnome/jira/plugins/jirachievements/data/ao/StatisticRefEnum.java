package com.madgnome.jira.plugins.jirachievements.data.ao;

public enum StatisticRefEnum
{
  CREATED_ISSUE_COUNT("CreatedIssueCount"),
  RESOLVED_ISSUE_COUNT("ResolvedIssueCount"),
  TESTED_ISSUE_COUNT("TestedIssueCount");

  private final String ref;

  StatisticRefEnum(String ref)
  {
    this.ref = ref;
  }

  @Override
  public String toString()
  {
    return ref;
  }
}
