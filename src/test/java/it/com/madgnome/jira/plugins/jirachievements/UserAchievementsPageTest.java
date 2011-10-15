package it.com.madgnome.jira.plugins.jirachievements;

import com.atlassian.integrationtesting.runner.restore.RestoreOnce;
import com.atlassian.jira.functest.framework.suite.Category;
import com.atlassian.jira.functest.framework.suite.WebTest;
import com.madgnome.jira.plugins.jirachievements.pageobjects.UserAchievementsPage;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

@WebTest({Category.WEBDRIVER_TEST, Category.PLUGINS})
@RestoreOnce("xml/TestUserAchievements.zip")
public class UserAchievementsPageTest extends BaseWebTest
{
  @Test
  public void shouldDisplayLevelsOnUserAchievementsPage()
  {
    UserAchievementsPage userAchievementsPage =
            jira.gotoLoginPage().login("user", "user", UserAchievementsPage.class);

    assertEquals(1, userAchievementsPage.getUserLevelCount());
    assertEquals(1, userAchievementsPage.getDeveloperLevelCount());
    assertEquals(1, userAchievementsPage.getTesterLevelCount());
  }
}
