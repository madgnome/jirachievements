package com.madgnome.jira.plugins.jirachievements.ui;

import com.atlassian.jira.plugin.profile.OptionalUserProfilePanel;
import com.atlassian.jira.plugin.profile.ViewProfilePanel;
import com.atlassian.jira.plugin.profile.ViewProfilePanelModuleDescriptor;
import com.atlassian.plugin.webresource.WebResourceManager;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.templaterenderer.TemplateRenderer;
import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.Category;
import com.madgnome.jira.plugins.jirachievements.data.services.IAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;
import com.opensymphony.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webwork.action.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

  public AchievementViewProfilePanel(TemplateRenderer templateRenderer, UserManager userManager, WebResourceManager webResourceManager, IAchievementDaoService achievementDaoService, IUserWrapperDaoService userWrapperDaoService)
  {
    this.templateRenderer = templateRenderer;
    this.userManager = userManager;
    this.webResourceManager = webResourceManager;
    this.achievementDaoService = achievementDaoService;
    this.userWrapperDaoService = userWrapperDaoService;
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

  private void render(HttpServletRequest req, Writer output, User user) throws IOException, Exception
  {
    String username = this.userManager.getRemoteUsername(req);
    if (username == null)
    {
      throw new Exception("Unauthorized - must be a valid user");
    }

    webResourceManager.requireResource("com.atlassian.auiplugin:ajs");
    webResourceManager.requireResource("com.madgnome.jira.plugins.jirachievements:jh-user-achievements-details");

    Map<String, Object> params = retrieveParameters();
    params.put("req", req);

    // TODO optimize
    Achievement[] userAchievements = userWrapperDaoService.getUserWrapper(user).getAchievements();
    params.put("userAchievements", userAchievements);

    templateRenderer.render("templates/com/madgnome/jira/plugins/jirachievements/achievements.vm", params, output);
  }

  private Map<String, Object> retrieveParameters()
  {
    Map<String, Object> params = new HashMap<String, Object>();

    Map<String, List<Achievement>> achievementByCategory = new HashMap<String, List<Achievement>>();
    List<Achievement> achievements = achievementDaoService.all();
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

    params.put("achievements", achievementByCategory);

    return params;
  }
}
