package com.madgnome.jira.plugins.jirachievements.ui.action;

import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.security.Permissions;
import com.atlassian.jira.util.I18nHelper;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.plugin.webresource.WebResourceManager;

public class PluginWebActionSupport extends JiraWebActionSupport
{
  private final JiraAuthenticationContext authenticationContext;
  private final WebResourceManager webResourceManager;
  private I18nHelper i18nHelper;

  public PluginWebActionSupport(JiraAuthenticationContext authenticationContext, WebResourceManager webResourceManager)
  {
    this.authenticationContext = authenticationContext;
    this.webResourceManager = webResourceManager;
  }

  public boolean hasPermissions()
  {
    return isHasPermission(Permissions.ADMINISTER);
  }

  @Override
  public String doDefault() throws Exception
  {
    if (!hasPermissions())
    {
			return PERMISSION_VIOLATION_RESULT;
		}


		return INPUT;
  }

  @Override
  protected String doExecute() throws Exception
  {
    webResourceManager.requireResource("com.madgnome.jira.plugins.jirachievements:jh-jquery-ui");
    webResourceManager.requireResource("com.madgnome.jira.plugins.jirachievements:jh-user-achievements-details");

    return super.doExecute();
  }
}
