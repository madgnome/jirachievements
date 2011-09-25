package com.madgnome.jira.plugins.jirachievements.services;

import com.atlassian.jira.config.ConstantsManager;
import com.atlassian.jira.issue.status.Status;
import com.madgnome.jira.plugins.jirachievements.data.ao.Config;
import com.madgnome.jira.plugins.jirachievements.data.services.IConfigDaoService;

import java.util.*;

public class WorkflowConfiguration
{
  private final IConfigDaoService configDaoService;
  private final ConstantsManager constantsManager;

  public enum NormalizedStatus
  {
    OPEN,
    RESOLVED,
    CLOSED,
    REOPENED
  }

  private Map<NormalizedStatus, String> statuses;

  public WorkflowConfiguration(IConfigDaoService configDaoService, ConstantsManager constantsManager)
  {
    this.configDaoService = configDaoService;
    this.constantsManager = constantsManager;

    statuses = new HashMap<NormalizedStatus, String>();
    statuses.put(NormalizedStatus.OPEN, "Open");
    statuses.put(NormalizedStatus.RESOLVED, "Resolved");
    statuses.put(NormalizedStatus.CLOSED, "Closed");
    statuses.put(NormalizedStatus.REOPENED, "Reopened ");
  }

  public List<String> getStatuses(NormalizedStatus status)
  {
    List<String> ids = getStatusesId(status);
    List<String> refs = new ArrayList<String>();
    for (String id : ids)
    {
      final Status statusObject = constantsManager.getStatusObject(id);
      if (statusObject != null)
      {
        refs.add(statusObject.getName());
      }
    }

    return refs;
  }

  public String getStatusesAsCSV(NormalizedStatus status)
  {
    StringBuilder builder = new StringBuilder();
    for (String ref : getStatuses(status))
    {
      builder.append(ref).append(",");
    }
    return builder.toString();
  }

  public List<String> getStatusesId(NormalizedStatus status)
  {
    final Config config = configDaoService.get(status.toString().toLowerCase() + "_workflow_statuses");
    return Arrays.asList(config.getValue().split(","));
  }

  public void saveStatuses(NormalizedStatus status, String[] statusesId)
  {
    List<String> ids = Arrays.asList(statusesId);
    Collections.sort(ids);
    StringBuilder builder = new StringBuilder();
    for (String id : ids)
    {
      builder.append(id).append(",");
    }

    configDaoService.setValue(status.toString().toLowerCase() + "_workflow_statuses", builder.toString());
  }
}
