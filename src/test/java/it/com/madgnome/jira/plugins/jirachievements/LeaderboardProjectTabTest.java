package it.com.madgnome.jira.plugins.jirachievements;

import com.atlassian.integrationtesting.runner.restore.RestoreOnce;
import com.atlassian.jira.functest.framework.suite.Category;
import com.atlassian.jira.functest.framework.suite.WebTest;
import com.atlassian.jira.pageobjects.pages.project.BrowseProjectPage;
import com.madgnome.jira.plugins.jirachievements.pageobjects.LeaderboardProjectTab;
import com.madgnome.jira.plugins.jirachievements.pageobjects.UserDetailsRow;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@WebTest({Category.WEBDRIVER_TEST, Category.PLUGINS})
@RestoreOnce("xml/TestUserAchievements.zip")
public class LeaderboardProjectTabTest extends BaseWebTest
{
  @Test
  public void shouldShowAdminAsLeadUserDeveloperAndTester()
  {
    BrowseProjectPage browseProjectPage = jira.gotoLoginPage().loginAsSysAdmin(BrowseProjectPage.class, "DBW");
    LeaderboardProjectTab leaderboardProjectTab = browseProjectPage.openTab(LeaderboardProjectTab.class);

    assertEquals("admin", leaderboardProjectTab.getUserLeader());
  }

  @Test
  public void firstUserInDetailsShouldBeLeader()
  {
    BrowseProjectPage browseProjectPage = jira.gotoLoginPage().loginAsSysAdmin(BrowseProjectPage.class, "DBW");
    LeaderboardProjectTab leaderboardProjectTab = browseProjectPage.openTab(LeaderboardProjectTab.class);

    UserDetailsRow userDetailsFirstRow = leaderboardProjectTab.getUserDetails().iterator().next();
    assertEquals(leaderboardProjectTab.getUserLeader(), userDetailsFirstRow.getUsername());
  }
}
