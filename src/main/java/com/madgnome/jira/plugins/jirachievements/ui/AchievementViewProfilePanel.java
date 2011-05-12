package com.madgnome.jira.plugins.jirachievements.ui;

import com.atlassian.jira.plugin.profile.OptionalUserProfilePanel;
import com.atlassian.jira.plugin.profile.ViewProfilePanel;
import com.atlassian.jira.plugin.profile.ViewProfilePanelModuleDescriptor;
import com.atlassian.plugin.webresource.WebResourceManager;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.templaterenderer.TemplateRenderer;
import com.madgnome.jira.plugins.jirachievements.data.ao.*;
import com.madgnome.jira.plugins.jirachievements.data.services.IAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.ILevelDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserStatisticDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;
import com.opensymphony.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webwork.action.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AchievementViewProfilePanel implements ViewProfilePanel, OptionalUserProfilePanel
{
  private static final Logger logger = LoggerFactory.getLogger(AchievementViewProfilePanel.class);

  private final TemplateRenderer templateRenderer;
  private final UserManager userManager;
  private final WebResourceManager webResourceManager;

  private final IAchievementDaoService achievementDaoService;
  private final IUserWrapperDaoService userWrapperDaoService;
  private final ILevelDaoService levelDaoService;
  private final IUserStatisticDaoService userStatisticDaoService;

  public AchievementViewProfilePanel(TemplateRenderer templateRenderer, UserManager userManager, WebResourceManager webResourceManager, IAchievementDaoService achievementDaoService, IUserWrapperDaoService userWrapperDaoService, ILevelDaoService levelDaoService, IUserStatisticDaoService userStatisticDaoService)
  {
    this.templateRenderer = templateRenderer;
    this.userManager = userManager;
    this.webResourceManager = webResourceManager;
    this.achievementDaoService = achievementDaoService;
    this.userWrapperDaoService = userWrapperDaoService;
    this.levelDaoService = levelDaoService;
    this.userStatisticDaoService = userStatisticDaoService;
  }

  @Override
  public boolean showPanel(User user, User user1)
  {
    return true;
  }

  @Override
  public void init(ViewProfilePanelModuleDescriptor viewProfilePanelModuleDescriptor)
  {
  }

  @Override
  public String getHtml(User user)
  {
    HttpServletRequest req = ServletActionContext.getRequest();
    StringWriter writer = new StringWriter();
    
    try
    {
      render(req, writer, user);
    }
    catch (Exception e)
    {
      writer.write("Unauthorized access: " + e.getMessage());
    }

    return writer.toString();
  }

  private void render(HttpServletRequest req, Writer output, User user) throws Exception
  {
    String username = this.userManager.getRemoteUsername(req);
    if (username == null)
    {
      throw new Exception("Unauthorized - must be a valid user");
    }

    webResourceManager.requireResource("com.atlassian.auiplugin:ajs");
    webResourceManager.requireResource("com.madgnome.jira.plugins.jirachievements:jh-user-achievements-details");

    UserWrapper userWrapper = userWrapperDaoService.get(user);
    Map<String, Object> params = retrieveParameters(userWrapper);
    params.put("req", req);
    params.put("userWrapper", userWrapper);

    templateRenderer.render("templates/com/madgnome/jira/plugins/jirachievements/achievements.vm", params, output);
  }

  private Map<String, Object> retrieveParameters(UserWrapper userWrapper)
  {
    Map<String, Object> params = new HashMap<String, Object>();

    Map<String, List<Achievement>> achievementByCategory = retrieveAchievementsByCategory();
    params.put("achievements", achievementByCategory);

    Achievement[] userAchievements = userWrapper.getAchievements();
    params.put("userAchievements", userAchievements);

    UserStatistic createdUserStatistic = userStatisticDaoService.get(userWrapper, StatisticRefEnum.CREATED_ISSUE_COUNT);
    Level userLevel = levelDaoService.findMatchingLevel(Category.USER, createdUserStatistic.getValue());
    params.put("userStatistic", createdUserStatistic);
    params.put("userLevel", userLevel);
    params.put("userPercentage", (userLevel.getMaxThreshold() - createdUserStatistic.getValue()) * 100 / userLevel.getMaxThreshold());

    UserStatistic resolvedUserStatistic = userStatisticDaoService.get(userWrapper, StatisticRefEnum.RESOLVED_ISSUE_COUNT);
    Level developerLevel = levelDaoService.findMatchingLevel(Category.DEVELOPER, resolvedUserStatistic.getValue());
    params.put("developerStatistic", resolvedUserStatistic);
    params.put("developerLevel", developerLevel);
    params.put("developerPercentage", (developerLevel.getMaxThreshold() - resolvedUserStatistic.getValue()) * 100 / developerLevel.getMaxThreshold());

    UserStatistic testedUserStatistic = userStatisticDaoService.get(userWrapper, StatisticRefEnum.TESTED_ISSUE_COUNT);
    Level testerLevel = levelDaoService.findMatchingLevel(Category.TESTER, testedUserStatistic.getValue());
    params.put("testerStatistic", testedUserStatistic);
    params.put("testerLevel", testerLevel);
    params.put("testerPercentage", (testerLevel.getMaxThreshold() - testedUserStatistic.getValue()) * 100 / testerLevel.getMaxThreshold());

    return params;
  }

  private Map<String, List<Achievement>> retrieveAchievementsByCategory()
  {
    Map<String, List<Achievement>> achievementByCategory = new HashMap<String, List<Achievement>>();
    List<Achievement> achievements = achievementDaoService.allActive();
    for (Achievement achievement : achievements)
    {
      Category category = achievement.getCategory();
      List<Achievement> categoryAchievements = achievementByCategory.get(category.toString());
      if (categoryAchievements == null)
      {
        categoryAchievements = new ArrayList<Achievement>();
        achievementByCategory.put(category.toString(), categoryAchievements);
      }

      categoryAchievements.add(achievement);
    }
    return achievementByCategory;
  }
}
