package com.madgnome.jira.plugins.jirachievements.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkflowConfiguration
{
  public enum NormalizedStatus
  {
    OPEN,
    RESOLVED,
    CLOSED,
    REOPENED
  }

  private Map<NormalizedStatus, String> statuses;

  public WorkflowConfiguration()
  {
    statuses = new HashMap<NormalizedStatus, String>();
    statuses.put(NormalizedStatus.OPEN, "Open");
    statuses.put(NormalizedStatus.RESOLVED, "Resolved");
    statuses.put(NormalizedStatus.CLOSED, "Closed");
    statuses.put(NormalizedStatus.REOPENED, "Reopened ");
  }

  public List<String> getStatuses(NormalizedStatus status)
  {
    return Arrays.asList(statuses.get(status).split(","));
  }

  public String getStatusesAsCSV(NormalizedStatus status)
  {
    return statuses.get(status);
  }
}
