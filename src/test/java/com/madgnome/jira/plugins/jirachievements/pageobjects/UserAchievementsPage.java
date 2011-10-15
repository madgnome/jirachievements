package com.madgnome.jira.plugins.jirachievements.pageobjects;

import com.atlassian.jira.pageobjects.pages.AbstractJiraPage;
import com.atlassian.pageobjects.elements.CheckboxElement;
import com.atlassian.pageobjects.elements.ElementBy;
import com.atlassian.pageobjects.elements.PageElement;
import com.atlassian.pageobjects.elements.query.TimedCondition;

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
}
