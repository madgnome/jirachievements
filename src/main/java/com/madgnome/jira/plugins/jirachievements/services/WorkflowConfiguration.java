package com.madgnome.jira.plugins.jirachievements.services;

import com.atlassian.jira.config.ConstantsManager;
import com.atlassian.jira.issue.status.Status;
import com.madgnome.jira.plugins.jirachievements.data.ao.Config;
import com.madgnome.jira.plugins.jirachievements.data.ao.ConfigRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.services.IConfigDaoService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WorkflowConfiguration
{
  private static final String WORKFLOW_STATUSES_SUFFIX = "_WORKFLOW_STATUSES";
  private final IConfigDaoService configDaoService;
  private final ConstantsManager constantsManager;

  public enum NormalizedStatus
  {
    OPEN,
    RESOLVED,
    CLOSED,
    REOPENED;

    public ConfigRefEnum toConfigRefEnum()
    {
      String name = this.toString();
      return ConfigRefEnum.valueOf(name + WORKFLOW_STATUSES_SUFFIX);
    }
  }

  public WorkflowConfiguration(IConfigDaoService configDaoService, ConstantsManager constantsManager)
  {
    this.configDaoService = configDaoService;
    this.constantsManager = constantsManager;
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
    List<String> statuses = getStatuses(status);
    for (int i = 0, statusesSize = statuses.size(); i < statusesSize; i++)
    {
      String ref = statuses.get(i);
      builder.append(ref);
      if (i < statusesSize - 1)
      {
        builder.append(",");
      }
    }
    return builder.toString();
  }

  public List<String> getStatusesId(NormalizedStatus status)
  {
    final Config config = configDaoService.get(status.toConfigRefEnum());
    return Arrays.asList(config.getValue().split(","));
  }

  public void saveStatuses(NormalizedStatus status, String[] statusesId)
  {
    List<String> ids = statusesId != null ? Arrays.asList(statusesId) : Collections.<String>emptyList();
    Collections.sort(ids);
    StringBuilder builder = new StringBuilder();
    for (String id : ids)
    {
      builder.append(id).append(",");
    }

    configDaoService.setValue(status.toConfigRefEnum(), builder.toString());
  }
}
