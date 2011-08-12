package com.madgnome.jira.plugins.jirachievements.ui;

import com.atlassian.jira.plugin.profile.OptionalUserProfilePanel;
import com.atlassian.jira.plugin.profile.ViewProfilePanel;
import com.atlassian.jira.plugin.profile.ViewProfilePanelModuleDescriptor;
import com.atlassian.plugin.webresource.WebResourceManager;
import com.atlassian.templaterenderer.TemplateRenderer;
import com.madgnome.jira.plugins.jirachievements.data.ao.*;
import com.madgnome.jira.plugins.jirachievements.data.services.IAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.ILevelDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserStatisticDaoService;
import com.madgnome.jira.plugins.jirachievements.services.UserManager;
import com.opensymphony.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webwork.action.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;

public class AchievementViewProfilePanel implements ViewProfilePanel, OptionalUserProfilePanel
{
  private static final Logger logger = LoggerFactory.getLogger(AchievementViewProfilePanel.class);

  private final TemplateRenderer templateRenderer;
  private final com.atlassian.sal.api.user.UserManager jiraUserManager;
  private final WebResourceManager webResourceManager;

  private final UserManager userManager;
  private final IAchievementDaoService achievementDaoService;
  private final ILevelDaoService levelDaoService;
  private final IUserStatisticDaoService userStatisticDaoService;

  public AchievementViewProfilePanel(TemplateRenderer templateRenderer,
                                     com.atlassian.sal.api.user.UserManager jiraUserManager,
                                     WebResourceManager webResourceManager,
                                     IAchievementDaoService achievementDaoService,
                                     ILevelDaoService levelDaoService,
                                     IUserStatisticDaoService userStatisticDaoService,
                                     UserManager userManager)
  {
    this.templateRenderer = templateRenderer;
    this.jiraUserManager = jiraUserManager;
    this.webResourceManager = webResourceManager;
    this.achievementDaoService = achievementDaoService;
    this.levelDaoService = levelDaoService;
    this.userStatisticDaoService = userStatisticDaoService;
    this.userManager = userManager;
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
      logger.error("An error occured while retrieving user achievements", e);
      renderErrorMessage(writer);
    }

    return writer.toString();
  }

  private void renderErrorMessage(StringWriter writer)
  {
    try
    {
      templateRenderer.render("templates/com/madgnome/jira/plugins/jirachievements/error.vm", Collections.<String, Object>emptyMap(), writer);
    }
    catch (IOException e)
    {

    }
  }


  private void render(HttpServletRequest req, Writer output, User user) throws Exception
  {
    String username = user.getName();
    if (username == null)
    {
      throw new Exception("Unauthorized - must be a valid user");
    }

    webResourceManager.requireResource("com.atlassian.auiplugin:ajs");
    webResourceManager.requireResource("com.madgnome.jira.plugins.jirachievements:jh-user-achievements-details");

    UserWrapper userWrapper = userManager.get(username);
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

    putCreatedUserStatistic(userWrapper, params);
    putResolvedUserStatistic(userWrapper, params);
    putTestedUserStatistic(userWrapper, params);

    return params;
  }

  private void putTestedUserStatistic(UserWrapper userWrapper, Map<String, Object> params)
  {
    UserStatistic testedUserStatistic = userStatisticDaoService.get(userWrapper, StatisticRefEnum.TESTED_ISSUE_COUNT);
    Level testerLevel = levelDaoService.findMatchingLevel(Category.TESTER, testedUserStatistic.getValue());
    params.put("testerStatistic", testedUserStatistic);
    params.put("testerLevel", testerLevel);
    params.put("testerPercentage", calculatePercentage(testerLevel.getMaxThreshold(), testedUserStatistic.getValue()));
  }

  private void putResolvedUserStatistic(UserWrapper userWrapper, Map<String, Object> params)
  {
    UserStatistic resolvedUserStatistic = userStatisticDaoService.get(userWrapper, StatisticRefEnum.RESOLVED_ISSUE_COUNT);
    Level developerLevel = levelDaoService.findMatchingLevel(Category.DEVELOPER, resolvedUserStatistic.getValue());
    params.put("developerStatistic", resolvedUserStatistic);
    params.put("developerLevel", developerLevel);
    params.put("developerPercentage", calculatePercentage(developerLevel.getMaxThreshold(), resolvedUserStatistic.getValue()));
  }

  private void putCreatedUserStatistic(UserWrapper userWrapper, Map<String, Object> params)
  {
    UserStatistic createdUserStatistic = userStatisticDaoService.get(userWrapper, StatisticRefEnum.CREATED_ISSUE_COUNT);
    Level userLevel = levelDaoService.findMatchingLevel(Category.USER, createdUserStatistic.getValue());
    params.put("userStatistic", createdUserStatistic);
    params.put("userLevel", userLevel);
    params.put("userPercentage", calculatePercentage(userLevel.getMaxThreshold(), createdUserStatistic.getValue()));
  }

  private int calculatePercentage(int maxThreshold, int value)
  {
    return value * 100 / maxThreshold;
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
