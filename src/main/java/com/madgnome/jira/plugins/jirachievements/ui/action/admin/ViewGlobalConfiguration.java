package com.madgnome.jira.plugins.jirachievements.ui.action.admin;

import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.plugin.webresource.WebResourceManager;
import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.services.IAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.ui.action.PluginWebActionSupport;

import java.util.List;
import java.util.Map;

public class ViewGlobalConfiguration extends PluginWebActionSupport
{
  private final IAchievementDaoService achievementDaoService;

  public ViewGlobalConfiguration(JiraAuthenticationContext authenticationContext, WebResourceManager webResourceManager, IAchievementDaoService achievementDaoService)
  {
    super(authenticationContext, webResourceManager);
    this.achievementDaoService = achievementDaoService;
  }

  public Map<String, List<Achievement>> getAchievementsByCategory()
  {
    return achievementDaoService.allGroupByCategoryName();
  }
}
