package com.madgnome.jira.plugins.jirachievements.pageobjects;

import com.atlassian.jira.pageobjects.pages.AbstractJiraPage;
import com.atlassian.pageobjects.elements.CheckboxElement;
import com.atlassian.pageobjects.elements.ElementBy;
import com.atlassian.pageobjects.elements.PageElement;
import com.atlassian.pageobjects.elements.query.TimedCondition;
import org.openqa.selenium.By;

public class UserAchievementsPage extends AbstractJiraPage
{
  private final static String URI = "/secure/ViewProfile.jspa#selectedTab=com.madgnome.jira.plugins.jirachievements:achievements-profile";

  @ElementBy(className = "active-area")
  private PageElement container;

  @ElementBy(id = "disable-for-user")
  private CheckboxElement kryptoniteCheckbox;

  @Override
  public TimedCondition isAt()
  {
    return container.timed().isPresent();
  }

  @Override
  public String getUrl()
  {
    return URI;
  }

  public UserAchievementsPage enableNotification()
  {
    kryptoniteCheckbox.uncheck();
    return this;
  }

  public UserAchievementsPage disableNotification()
  {
    kryptoniteCheckbox.check();
    return this;
  }

  public PageElement getAchievementElement(int id)
  {
    return container.find(By.id("achievement-" + String.valueOf(id)));
  }

  public boolean achievementIsPresent(int id)
  {
    return getAchievementElement(id).isPresent();
  }

  public boolean hasAchievement(int id)
  {
    return getAchievementElement(id).hasClass("active");
  }

  public int getUserLevelCount()
  {
    return Integer.parseInt(getLevelCount("userLevel").getText());
  }

  public int getDeveloperLevelCount()
  {
    return Integer.parseInt(getLevelCount("developerLevel").getText());
  }

  public int getTesterLevelCount()
  {
    return Integer.parseInt(getLevelCount("testerLevel").getText());
  }

  private PageElement getLevelCount(String id)
  {
    return container.find(By.id(id)).find(By.className("count"));
  }
}
