package com.madgnome.jira.plugins.jirachievements.ui.action.admin;

import com.atlassian.jira.issue.status.Status;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.plugin.webresource.WebResourceManager;
import com.madgnome.jira.plugins.jirachievements.services.WorkflowConfiguration;
import com.madgnome.jira.plugins.jirachievements.ui.action.PluginWebActionSupport;

import java.util.Collection;

public class ViewAdvancedConfiguration extends PluginWebActionSupport
{
  private final WorkflowConfiguration workflowConfiguration;

  private boolean submitted = false;
  private String[] userStatuses;
  private String[] developerStatuses;
  private String[] testerStatuses;

  public ViewAdvancedConfiguration(JiraAuthenticationContext authenticationContext, WebResourceManager webResourceManager, WorkflowConfiguration workflowConfiguration)
  {
    super(authenticationContext, webResourceManager);
    this.workflowConfiguration = workflowConfiguration;
  }

  @Override
  protected String doExecute() throws Exception
  {
    if (!isSubmitted())
    {
      return "success";
    }

    workflowConfiguration.saveStatuses(WorkflowConfiguration.NormalizedStatus.OPEN, userStatuses);
    workflowConfiguration.saveStatuses(WorkflowConfiguration.NormalizedStatus.RESOLVED, developerStatuses);
    workflowConfiguration.saveStatuses(WorkflowConfiguration.NormalizedStatus.CLOSED, testerStatuses);

    return getRedirect("ViewJIRAHeroAdvancedConfiguration.jspa");
  }

  public boolean isSubmitted()
  {
    return submitted;
  }

  public void setSubmitted(boolean submitted)
  {
    this.submitted = submitted;
  }

  public String[] getUserStatuses()
  {
    return userStatuses;
  }

  public void setUserStatuses(String[] userStatuses)
  {
    this.userStatuses = userStatuses;
  }

  public String[] getDeveloperStatuses()
  {
    return developerStatuses;
  }

  public void setDeveloperStatuses(String[] developerStatuses)
  {
    this.developerStatuses = developerStatuses;
  }

  public String[] getTesterStatuses()
  {
    return testerStatuses;
  }

  public void setTesterStatuses(String[] testerStatuses)
  {
    this.testerStatuses = testerStatuses;
  }

  public Collection<Status> getStatuses()
  {
    return getConstantsManager().getStatusObjects();
  }

  public WorkflowConfiguration.NormalizedStatus[] getNormalizedStatuses()
  {
    return WorkflowConfiguration.NormalizedStatus.values();
  }

  public Collection<String> getCurrentStatus(String status)
  {
    return workflowConfiguration.getStatuses(WorkflowConfiguration.NormalizedStatus.valueOf(status));
  }

  public boolean isSelected(String normalizedStatus, String status)
  {
    // TODO Optimize
    return workflowConfiguration.getStatusesId(WorkflowConfiguration.NormalizedStatus.valueOf(normalizedStatus)).contains(status);
  }

  public boolean selected(String normalizedStatus, String status)
  {
    return isSelected(normalizedStatus, status);
  }
}
