package it.com.madgnome.jira.plugins.jirachievements;

import com.atlassian.integrationtesting.runner.restore.RestoreOnce;
import com.atlassian.jira.functest.framework.suite.Category;
import com.atlassian.jira.functest.framework.suite.WebTest;
import com.madgnome.jira.plugins.jirachievements.pageobjects.HeaderUserAchievements;
import com.madgnome.jira.plugins.jirachievements.pageobjects.JiraDashboardPage;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

@WebTest({Category.WEBDRIVER_TEST, Category.PLUGINS})
@RestoreOnce("xml/TestHeaderUserAchievements.zip")
public class HeaderUserAchievementsTest extends BaseWebTest
{
  @Test
  public void adminHasAtLeastOneBronzeBadge()
  {
    HeaderUserAchievements headerUserAchievements =
            jira.gotoLoginPage().loginAsSysAdmin(JiraDashboardPage.class).getHeaderUserAchievements();

    assertTrue(headerUserAchievements.getBronzeBadgesCount() > 0);
  }

  @Test
  public void userHasAtLeastOneBronzeBadge()
  {
    HeaderUserAchievements headerUserAchievements =
            jira.gotoLoginPage().login("user", "user", JiraDashboardPage.class).getHeaderUserAchievements();

    assertTrue(headerUserAchievements.getBronzeBadgesCount() > 0);
  }
}
