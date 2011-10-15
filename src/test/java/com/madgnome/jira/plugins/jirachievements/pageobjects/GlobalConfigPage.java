package com.madgnome.jira.plugins.jirachievements.pageobjects;

import com.atlassian.jira.pageobjects.pages.AbstractJiraAdminPage;
import com.atlassian.pageobjects.elements.ElementBy;
import com.atlassian.pageobjects.elements.PageElement;
import com.atlassian.pageobjects.elements.query.TimedCondition;
import com.atlassian.pageobjects.elements.timeout.TimeoutType;
import org.openqa.selenium.By;

public class GlobalConfigPage extends AbstractJiraAdminPage
{
  private final static String URI = "/secure/admin/ViewJIRAHeroGlobalConfiguration.jspa";
  private final static String LINK_ID = "jh-config-webfragment";

  @ElementBy(className = "admin-active-area")
  private PageElement configContainer;

  @ElementBy(id = "header-user-achievements", timeoutType = TimeoutType.AJAX_ACTION)
  HeaderUserAchievements headerUserAchievements;

  @Override
  public String linkId()
  {
    return LINK_ID;
  }

  @Override
  public TimedCondition isAt()
  {
    return configContainer.timed().isPresent();
  }

  @Override
  public String getUrl()
  {
    return URI;
  }

  public GlobalConfigPage disableAchievement(int achievementId)
  {
    PageElement achievement = getAchievementElement(achievementId);

    if (achievement.find(By.className("active")) != null)
    {
      // Achievement is enable, else do nothing
      achievement.click();
    }

    return this;
  }

  public GlobalConfigPage enableAchievement(int achievementId)
  {
    PageElement achievement = getAchievementElement(achievementId);

    if (!achievement.find(By.className("mask")).hasClass("active"))
    {
      // Achievement is enable, else do nothing
      achievement.click();
    }

    return this;
  }

  public PageElement getAchievementElement(int id)
  {
    return configContainer.find(By.id("achievement-" + String.valueOf(id)));
  }

  public HeaderUserAchievements getHeaderUserAchievements()
  {
    return headerUserAchievements;
  }
}
