package com.madgnome.jira.plugins.jirachievements.ui.action.admin;

import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.plugin.webresource.WebResourceManager;
import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.ConfigRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.services.IAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IConfigDaoService;
import com.madgnome.jira.plugins.jirachievements.ui.action.PluginWebActionSupport;

import java.util.List;
import java.util.Map;

public class ViewGlobalConfiguration extends PluginWebActionSupport
{
  private final IAchievementDaoService achievementDaoService;
  private final IConfigDaoService configDaoService;

  public ViewGlobalConfiguration(JiraAuthenticationContext authenticationContext, WebResourceManager webResourceManager, IAchievementDaoService achievementDaoService, IConfigDaoService configDaoService)
  {
    super(authenticationContext, webResourceManager);
    this.achievementDaoService = achievementDaoService;
    this.configDaoService = configDaoService;
  }

  public Map<String, List<Achievement>> getAchievementsByCategory()
  {
    return achievementDaoService.allGroupByCategoryName();
  }

  public int getAchievementRefreshRate()
  {
    return Integer.parseInt(configDaoService.get(ConfigRefEnum.ACHIEVEMENTS_REFRESH_RATE).getValue());
  }
}
