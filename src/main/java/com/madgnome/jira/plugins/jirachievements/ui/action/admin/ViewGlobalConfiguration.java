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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class ViewGlobalConfiguration extends PluginWebActionSupport
{
  private final static Logger logger = LoggerFactory.getLogger(ViewGlobalConfiguration.class);

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

  @Override
  protected String doExecute() throws Exception
  {
    String result = super.doExecute();

    int achievementsCount = achievementDaoService.all().size();
    int configCount = configDaoService.all().size();
    int levelsCount = levelDaoService.all().size();
    if (achievementsCount == 0 ||
        configCount == 0 ||
        levelsCount == 0)
    {
      String errorMessage = "JIRA Hero Database is not initialize properly. \n" +
                            "Achievements table count: {}\n" +
                            "Configuration table count: {}\n" +
                            "Levels table count: {}";
      logger.error(errorMessage, new Object[] {achievementsCount, configCount, levelsCount});
      
      return "error";
    }

    return result;
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
