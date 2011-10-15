package it.com.madgnome.jira.plugins.jirachievements;

import com.atlassian.integrationtesting.runner.restore.RestoreOnce;
import com.atlassian.jira.functest.framework.suite.Category;
import com.atlassian.jira.functest.framework.suite.WebTest;

@WebTest({Category.WEBDRIVER_TEST, Category.PLUGINS})
@RestoreOnce("xml/TestHeaderUserAchievements.zip")
public class UserAchievementsPageTest extends BaseWebTest
{
//  @Test
//  public void notNotifiedIfNotificationDisableInProfile()
//  {
//    UserAchievementsPage page =
//            jira.gotoLoginPage().login("user", "user", UserAchievementsPage.class);
//
//    page.disableNotification();
//    jira.visit(UserAchievementsPage.class);
//  }
}
