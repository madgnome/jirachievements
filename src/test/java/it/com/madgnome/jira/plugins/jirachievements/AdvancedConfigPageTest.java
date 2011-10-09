package it.com.madgnome.jira.plugins.jirachievements;

import com.atlassian.integrationtesting.runner.restore.RestoreOnce;
import com.atlassian.jira.functest.framework.suite.Category;
import com.atlassian.jira.functest.framework.suite.WebTest;
import com.atlassian.jira.pageobjects.JiraTestedProduct;
import com.atlassian.pageobjects.PageBinder;
import com.google.inject.Inject;
import com.madgnome.jira.plugins.jirachievements.pageobjects.AdvancedConfigPage;
import com.madgnome.jira.plugins.jirachievements.pageobjects.config.SingleJiraWebTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(SingleJiraWebTestRunner.class)
@WebTest({Category.WEBDRIVER_TEST, Category.PLUGINS, Category.ADMINISTRATION})
@RestoreOnce("xml/TestConfiguration.zip")
public class AdvancedConfigPageTest
{
  private static final String OPEN_STATUS = "Open";
  private static final String RESOLVED_STATUS = "Resolved";
  private static final String CLOSED_STATUS = "Closed";
  
  @Inject protected JiraTestedProduct jira;
  @Inject protected PageBinder pageBinder;

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
