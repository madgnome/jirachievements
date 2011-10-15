package it.com.madgnome.jira.plugins.jirachievements;

import com.atlassian.integrationtesting.runner.restore.RestoreOnce;
import com.atlassian.jira.functest.framework.suite.Category;
import com.atlassian.jira.functest.framework.suite.WebTest;
import com.madgnome.jira.plugins.jirachievements.pageobjects.GlobalConfigPage;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

@WebTest({Category.WEBDRIVER_TEST, Category.PLUGINS, Category.ADMINISTRATION})
@RestoreOnce("xml/TestConfiguration.zip")
public class GlobalConfigPageTest extends BaseWebTest
{
  @Test
  public void disabledAchievementShouldntCountInUserCount()
  {
    GlobalConfigPage page =
            jira.gotoLoginPage().loginAsSysAdmin(GlobalConfigPage.class);

    assertEquals(1, page.getHeaderUserAchievements().getBronzeBadgesCount());
    page.disableAchievement(1);
    
    page = jira.goTo(GlobalConfigPage.class);

    assertEquals(0, page.getHeaderUserAchievements().getBronzeBadgesCount());
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
}
