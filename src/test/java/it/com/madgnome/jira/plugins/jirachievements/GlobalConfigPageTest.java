package it.com.madgnome.jira.plugins.jirachievements;

import com.atlassian.integrationtesting.runner.restore.RestoreOnce;
import com.atlassian.jira.functest.framework.suite.Category;
import com.atlassian.jira.functest.framework.suite.WebTest;
import com.madgnome.jira.plugins.jirachievements.pageobjects.GlobalConfigPage;
import com.madgnome.jira.plugins.jirachievements.pageobjects.UserAchievementsPage;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

@WebTest({Category.WEBDRIVER_TEST, Category.PLUGINS, Category.ADMINISTRATION})
@RestoreOnce("xml/TestConfiguration.zip")
public class GlobalConfigPageTest extends BaseWebTest
{
  @Test
  public void disabledAchievementShouldntCountInUserCount()
  {
    GlobalConfigPage globalConfigPage =
            jira.gotoLoginPage().loginAsSysAdmin(GlobalConfigPage.class);

    assertEquals(1, globalConfigPage.getHeaderUserAchievements().getBronzeBadgesCount());
    globalConfigPage.disableAchievement(1);
    
    globalConfigPage = jira.goTo(GlobalConfigPage.class);

    assertEquals(0, globalConfigPage.getHeaderUserAchievements().getBronzeBadgesCount());

    globalConfigPage = jira.goTo(GlobalConfigPage.class);
    globalConfigPage.enableAchievement(1);
  }

  @Test
  public void enabledAchievementShouldCountInUserCount()
  {
    GlobalConfigPage page =
            jira.gotoLoginPage().loginAsSysAdmin(GlobalConfigPage.class);

    assertEquals(1, page.getHeaderUserAchievements().getBronzeBadgesCount());
    page.disableAchievement(1);
    page = jira.goTo(GlobalConfigPage.class);

    page.enableAchievement(1);
    page = jira.goTo(GlobalConfigPage.class);
    assertEquals(1, page.getHeaderUserAchievements().getBronzeBadgesCount());
  }

  @Test
  public void disabledAchievementShouldntBeDisplayedOnUserAchievementsPage()
  {
     GlobalConfigPage globalConfigPage =
            jira.gotoLoginPage().loginAsSysAdmin(GlobalConfigPage.class);

    assertEquals(1, globalConfigPage.getHeaderUserAchievements().getBronzeBadgesCount());
    globalConfigPage.disableAchievement(1);

    UserAchievementsPage userAchievementsPage = jira.goTo(UserAchievementsPage.class);
    assertFalse(userAchievementsPage.achievementIsPresent(1));

    globalConfigPage = jira.goTo(GlobalConfigPage.class);
    globalConfigPage.enableAchievement(1);
  }


}
