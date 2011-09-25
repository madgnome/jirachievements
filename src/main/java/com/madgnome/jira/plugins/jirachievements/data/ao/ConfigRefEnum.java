package com.madgnome.jira.plugins.jirachievements.data.ao;

public enum ConfigRefEnum
{
  OPEN_WORKFLOW_STATUSES("open_workflow_statuses"),
  RESOLVED_WORKFLOW_STATUSES("resolved_workflow_statuses"),
  CLOSED_WORKFLOW_STATUSES("closed_workflow_statuses"),
  REOPENED_WORKFLOW_STATUSES("reopened_workflow_statuses"),
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
