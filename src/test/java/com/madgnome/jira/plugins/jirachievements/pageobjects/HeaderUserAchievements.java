package com.madgnome.jira.plugins.jirachievements.pageobjects;

import com.atlassian.pageobjects.elements.PageElement;
import com.atlassian.pageobjects.elements.WebDriverElement;
import com.atlassian.pageobjects.elements.WebDriverLocatable;
import com.atlassian.pageobjects.elements.timeout.TimeoutType;
import org.openqa.selenium.By;

public class HeaderUserAchievements extends WebDriverElement implements PageElement
{
  public HeaderUserAchievements(By locator)
  {
    super(locator);
  }

  public HeaderUserAchievements(By locator, TimeoutType timeoutType)
  {
    super(locator, timeoutType);
  }

  public HeaderUserAchievements(By locator, WebDriverLocatable parent)
  {
    super(locator, parent);
  }

  public HeaderUserAchievements(By locator, WebDriverLocatable parent, TimeoutType timeoutType)
  {
    super(locator, parent, timeoutType);
  }

  public HeaderUserAchievements(WebDriverLocatable locatable, TimeoutType timeoutType)
  {
    super(locatable, timeoutType);
  }

  public int getBronzeBadgesCount()
  {
    return Integer.valueOf(find(By.id("achievement-bronze-count")).getText());
  }
}
