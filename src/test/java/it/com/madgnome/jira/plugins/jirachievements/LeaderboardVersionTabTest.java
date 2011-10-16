package it.com.madgnome.jira.plugins.jirachievements;

import com.atlassian.integrationtesting.runner.restore.RestoreOnce;
import com.atlassian.jira.functest.framework.suite.Category;
import com.atlassian.jira.functest.framework.suite.WebTest;
import com.atlassian.jira.pageobjects.pages.project.BrowseProjectPage;
import com.atlassian.jira.pageobjects.pages.project.VersionsTab;
import com.atlassian.jira.pageobjects.pages.project.browseversion.BrowseVersionPage;
import com.madgnome.jira.plugins.jirachievements.pageobjects.LeaderboardVersionTab;
import com.madgnome.jira.plugins.jirachievements.pageobjects.UserDetailsRow;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@WebTest({Category.WEBDRIVER_TEST, Category.PLUGINS})
@RestoreOnce("xml/TestUserAchievements.zip")
public class LeaderboardVersionTabTest extends BaseWebTest
{
  @Test
  public void shouldShowAdminAsLeadUserDeveloperAndTester()
  {
    BrowseProjectPage browseProjectPage = jira.gotoLoginPage().loginAsSysAdmin(BrowseProjectPage.class, "DBW");
    BrowseVersionPage browseVersionPage = browseProjectPage.openTab(VersionsTab.class).getVersion("NEXT").goToBrowseVersion();

    LeaderboardVersionTab leaderboardVersionTab = browseVersionPage.openTab(LeaderboardVersionTab.class);

    assertEquals("admin", leaderboardVersionTab.getUserLeader());
  }

  @Test
  public void firstUserInDetailsShouldBeLeader()
  {
    BrowseProjectPage browseProjectPage = jira.gotoLoginPage().loginAsSysAdmin(BrowseProjectPage.class, "DBW");
    BrowseVersionPage browseVersionPage = browseProjectPage.openTab(VersionsTab.class).getVersion("NEXT").goToBrowseVersion();

    LeaderboardVersionTab leaderboardVersionTab = browseVersionPage.openTab(LeaderboardVersionTab.class);

    UserDetailsRow userDetailsFirstRow = leaderboardVersionTab.getUserDetails().iterator().next();
    assertEquals(leaderboardVersionTab.getUserLeader(), userDetailsFirstRow.getUsername());
  }
}
