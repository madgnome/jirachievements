package it.com.madgnome.jira.plugins.jirachievements;

import com.atlassian.integrationtesting.runner.restore.RestoreOnce;
import com.atlassian.jira.functest.framework.suite.Category;
import com.atlassian.jira.functest.framework.suite.WebTest;
import com.madgnome.jira.plugins.jirachievements.pageobjects.AdvancedConfigPage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@WebTest({Category.WEBDRIVER_TEST, Category.PLUGINS, Category.ADMINISTRATION})
@RestoreOnce("xml/TestConfiguration.zip")
public class AdvancedConfigPageTest extends BaseWebTest
{
  private static final String OPEN_STATUS = "Open";
  private static final String RESOLVED_STATUS = "Resolved";
  private static final String CLOSED_STATUS = "Closed";

   @Before
   public final void setUp()
   {

   }

  @Test
  public void defaultStatusesIsSelected()
  {
    AdvancedConfigPage advancedConfigPage = jira.gotoLoginPage().loginAsSysAdmin(AdvancedConfigPage.class);

    assertEquals(OPEN_STATUS, advancedConfigPage.getSelectedUserStatuses());
    assertEquals(RESOLVED_STATUS, advancedConfigPage.getSelectedDeveloperStatuses());
    assertEquals(CLOSED_STATUS, advancedConfigPage.getSelectedTesterStatuses());
  }

  @Test
  public void testSuccessfulEdit()
  {
    AdvancedConfigPage advancedConfigPage = jira.gotoLoginPage().loginAsSysAdmin(AdvancedConfigPage.class);

    advancedConfigPage.setUserStatuses(CLOSED_STATUS)
                      .setDeveloperStatuses(OPEN_STATUS)
                      .setTesterStatuses(RESOLVED_STATUS)
                      .submit();

    assertEquals(CLOSED_STATUS, advancedConfigPage.getSelectedUserStatuses());
    assertEquals(OPEN_STATUS, advancedConfigPage.getSelectedDeveloperStatuses());
    assertEquals(RESOLVED_STATUS, advancedConfigPage.getSelectedTesterStatuses());
  }
}
