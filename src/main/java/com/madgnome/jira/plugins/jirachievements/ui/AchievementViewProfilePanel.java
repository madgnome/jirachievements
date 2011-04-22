package com.madgnome.jira.plugins.jirachievements.ui;

import com.atlassian.jira.plugin.profile.OptionalUserProfilePanel;
import com.atlassian.jira.plugin.profile.ViewProfilePanel;
import com.atlassian.jira.plugin.profile.ViewProfilePanelModuleDescriptor;
import com.atlassian.plugin.webresource.WebResourceManager;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.templaterenderer.TemplateRenderer;
import com.opensymphony.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webwork.action.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;

public class AchievementViewProfilePanel implements ViewProfilePanel, OptionalUserProfilePanel
{
  private static final Logger log = LoggerFactory.getLogger(AchievementViewProfilePanel.class);

  private final TemplateRenderer templateRenderer;
  private final UserManager userManager;
  private final WebResourceManager webResourceManager;

  public AchievementViewProfilePanel(TemplateRenderer templateRenderer, UserManager userManager, WebResourceManager webResourceManager)
  {
    this.templateRenderer = templateRenderer;
    this.userManager = userManager;
    this.webResourceManager = webResourceManager;
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
      render(req, writer);
    }
    catch (Exception e)
    {
      writer.write("Unauthorized access: " + e.getMessage());
    }
//    catch (IOException e)
//    {
//      writer.write("Unable to render panel: " + e.getMessage());
//      log.error("Error rendering speakeasy panel", e);
//    }

    return writer.toString();
  }

  private void render(HttpServletRequest req, Writer output) throws IOException, Exception
  {
    String user = this.userManager.getRemoteUsername(req);
    if (user == null)
    {
      throw new Exception("Unauthorized - must be a valid user");
    }

    this.webResourceManager.requireResource("com.atlassian.auiplugin:ajs");
    this.webResourceManager.requireResourcesForContext("speakeasy.user-profile");

    templateRenderer.render("templates/com/madgnome/jira/plugins/jirachievements/achievements.vm", Collections.EMPTY_MAP, output);
  }
}
