package com.madgnome.jira.plugins.jirachievements.ui.action.admin;

import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.plugin.webresource.WebResourceManager;
import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.Category;
import com.madgnome.jira.plugins.jirachievements.data.ao.ConfigRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.ao.Level;
import com.madgnome.jira.plugins.jirachievements.data.services.IAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IConfigDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.ILevelDaoService;
import com.madgnome.jira.plugins.jirachievements.ui.action.PluginWebActionSupport;

import java.util.List;
import java.util.Map;

public class ViewGlobalConfiguration extends PluginWebActionSupport
{
  private final IAchievementDaoService achievementDaoService;
  private final IConfigDaoService configDaoService;
  private final ILevelDaoService levelDaoService;

  public ViewGlobalConfiguration(JiraAuthenticationContext authenticationContext, WebResourceManager webResourceManager, IAchievementDaoService achievementDaoService, IConfigDaoService configDaoService, ILevelDaoService levelDaoService)
  {
    super(authenticationContext, webResourceManager);
    this.achievementDaoService = achievementDaoService;
    this.configDaoService = configDaoService;
    this.levelDaoService = levelDaoService;
  }

  public Map<String, List<Achievement>> getAchievementsByCategory()
  {
    return achievementDaoService.allGroupByCategoryName();
  }

  public int getAchievementRefreshRate()
  {
    return Integer.parseInt(configDaoService.get(ConfigRefEnum.ACHIEVEMENTS_REFRESH_RATE).getValue());
  }

  public int getStatisticsRefreshRate()
  {
    return Integer.parseInt(configDaoService.get(ConfigRefEnum.STATISTICS_REFRESH_RATE).getValue());
  }

  public List<Level> getLevels(String category)
  {
    return levelDaoService.all(Category.valueOf(category.toUpperCase()));
  }
}
