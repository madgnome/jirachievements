package com.madgnome.jira.plugins.jirachievements.pageobjects;

import com.atlassian.jira.pageobjects.pages.DashboardPage;
import com.atlassian.pageobjects.elements.ElementBy;
import com.atlassian.pageobjects.elements.query.TimedCondition;
import com.atlassian.pageobjects.elements.timeout.TimeoutType;

public class JiraDashboardPage extends DashboardPage
{
  @ElementBy(id = "header-user-achievements", timeoutType = TimeoutType.SLOW_PAGE_LOAD)
  HeaderUserAchievements headerUserAchievements;

  @Override
  public TimedCondition isAt()
  {
    //elementFinder.find(By.id("header-user-achievements"), TimedQuery<HeaderUserAchievements>. )
    return headerUserAchievements.timed().isPresent();
  }

  public HeaderUserAchievements getHeaderUserAchievements()
  {
    return headerUserAchievements;
  }
} 
