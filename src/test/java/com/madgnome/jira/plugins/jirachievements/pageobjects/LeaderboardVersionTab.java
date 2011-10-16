package com.madgnome.jira.plugins.jirachievements.pageobjects;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.elements.ElementBy;
import com.atlassian.pageobjects.elements.PageElement;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import org.openqa.selenium.By;

import javax.inject.Inject;

public class LeaderboardVersionTab extends AbstractVersionTab
{
  public static String LINK_ID = "version-leaderboard-panel-panel";

  @ElementBy(className = "content")
  private PageElement container;

  @ElementBy(id = "user-details")
  private PageElement userDetails;

  @ElementBy(id = "developer-details")
  private PageElement developerDetails;

  @ElementBy(id = "tester-details")
  private PageElement testerDetails;

  @Inject
  private PageBinder pageBinder;

  public LeaderboardVersionTab()
  {
    super(LINK_ID);
  }

  public String getUserLeader()
  {
    return getLeader("userLeader");
  }

  public String getDeveloperLeader()
  {
    return getLeader("developerLeader");
  }

  public String getTesterLeader()
  {
    return getLeader("testerLeader");
  }

  private String getLeader(String id)
  {
    return container.find(By.id(id)).find(By.tagName("a")).getText();
  }

  public Iterable<UserDetailsRow> getUserDetails()
  {
    return Iterables.transform(userDetails.findAll(By.tagName("tr")), new Function<PageElement, UserDetailsRow>()
    {
      @Override
      public UserDetailsRow apply(PageElement from)
      {
        return pageBinder.bind(UserDetailsRow.class, from);
      }
    });
  }
}
