package com.madgnome.jira.plugins.jirachievements.pageobjects.config;

import com.atlassian.jira.pageobjects.JiraTestedProduct;
import com.atlassian.jira.pageobjects.config.EnvironmentBasedProductInstance;
import com.google.common.collect.Lists;
import org.junit.runner.notification.RunListener;
import org.junit.runners.model.InitializationError;

/**
 * Runner for JIRA web tests to run as single test classes, constructor-compatible with the
 * {@link org.junit.runner.RunWith} annotation
 *
 * @since v4.4
 */
public class SingleJiraWebTestRunner extends CustomJiraWebTestRunner
{
  public SingleJiraWebTestRunner(Class<?> klass) throws InitializationError
  {
    super(klass, newLocalTestedProduct(), listeners());
  }

  private static JiraTestedProduct newLocalTestedProduct()
  {
    return new JiraTestedProduct(new EnvironmentBasedProductInstance());
  }

  private static Iterable<RunListener> listeners()
  {
    return  Lists.newArrayList();
//    return Lists.newArrayList(
//            LogPageSourceListener.asRunListener(),
//            DumpPageSourceListener.asRunListener(),
//            ScreenshotListener.asRunListener());
  }
}
