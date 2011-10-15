package com.madgnome.jira.plugins.jirachievements.pageobjects.config;

import com.atlassian.integrationtesting.runner.CompositeTestRunner;
import com.atlassian.jira.functest.framework.suite.JUnit4WebTestListener;
import com.atlassian.jira.functest.framework.suite.SuiteTransform;
import com.atlassian.jira.functest.framework.suite.TransformableRunner;
import com.atlassian.jira.functest.framework.suite.TransformingParentRunner;
import com.atlassian.jira.pageobjects.JiraTestedProduct;
import com.atlassian.jira.pageobjects.config.JiraSetupComposer;
import com.atlassian.jira.pageobjects.config.PageObjectsInjector;
import com.atlassian.jira.pageobjects.config.PrepareBrowser;
import com.atlassian.jira.pageobjects.config.RestoreJiraFromBackup;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.junit.runner.Description;
import org.junit.runner.notification.RunListener;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CustomJiraWebTestRunner extends CompositeTestRunner implements TransformableRunner<CustomJiraWebTestRunner>
{
  public static CompositeTestRunner.Composer jiraComposer(JiraTestedProduct product, Iterable<RunListener> listeners)
    {
        return compose()
                .beforeTestClass(new AddListeners(listeners))
                .afterTestClass(new RemoveListeners(listeners))
                .from(PageObjectsInjector.compose(product))
                .from(PrepareBrowser.cleanUpCookies(product))
                .from(PrepareBrowser.maximizeWindow(product))
                .from(JiraSetupComposer.compose(product))
//                .from(WebSudoComposer.globalDisable(product))
                .from(RestoreJiraFromBackup.compose(product));
//                .from(WebSudoComposer.enableIfRequested(product));
    }

    private final List<RunListener> listeners = Lists.newArrayList();
    private final List<SuiteTransform> transforms = Lists.newArrayList();
    private final JiraTestedProduct product;

    public CustomJiraWebTestRunner(Class<?> klass, JiraTestedProduct product, Iterable<RunListener> listeners) throws InitializationError
    {
        this(klass, product, listeners, Collections.<SuiteTransform>emptyList());
    }

    private CustomJiraWebTestRunner(Class<?> klass, JiraTestedProduct product, Iterable<RunListener> listeners,
            Iterable<SuiteTransform> transforms) throws InitializationError
    {
        super(klass, jiraComposer(product, listeners));
        this.product = product;
        Iterables.addAll(this.listeners, listeners);
        injectStuffToListeners(product);
        Iterables.addAll(this.transforms, transforms);
    }

    private void injectStuffToListeners(JiraTestedProduct product)
    {
        for (RunListener runListener : listeners)
        {
            product.injector().injectMembers(runListener);
            if (runListener instanceof JUnit4WebTestListener)
            {
                 product.injector().injectMembers(((JUnit4WebTestListener)runListener).webTestListener());
            }
        }
    }

    public CustomJiraWebTestRunner(Class<?> klass, JiraTestedProduct product) throws InitializationError
    {
        this(klass, product, Collections.<RunListener>emptyList());
    }

    public CustomJiraWebTestRunner withTransforms(List<SuiteTransform> transforms) throws InitializationError
    {
        return new CustomJiraWebTestRunner(getTestClass().getJavaClass(), product, listeners, transforms);
    }

    @Override
    protected List<FrameworkMethod> getChildren()
    {
        List<Description> descriptions = Lists.transform(super.getChildren(), new Function<FrameworkMethod, Description>()
        {
            @Override
            public Description apply(@Nullable FrameworkMethod from)
            {
                return describeChild(from);
            }
        });
        descriptions = TransformingParentRunner.applyTransforms(descriptions, transforms);
        List<FrameworkMethod> answer = Lists.newArrayList();
        for (FrameworkMethod child : super.getChildren())
        {
            if (descriptions.contains(describeChild(child)))
            {
                answer.add(child);
            }
        }
        return answer;
    }

    private static final class AddListeners implements Function<CompositeTestRunner.BeforeTestClass, Void>
    {
        private final Iterable<RunListener> listeners;

        public AddListeners(Iterable<RunListener> listeners)
        {
            this.listeners = listeners;
        }

        @Override
        public Void apply(@Nullable CompositeTestRunner.BeforeTestClass from)
        {
            for (RunListener listener : listeners)
            {
                from.notifier.addListener(listener);
            }
            return null;
        }
    }

    private static final class RemoveListeners implements Function<CompositeTestRunner.AfterTestClass, Void>
    {
        private final Iterable<RunListener> listeners;

        public RemoveListeners(Iterable<RunListener> listeners)
        {
            this.listeners = listeners;
        }

        @Override
        public Void apply(@Nullable CompositeTestRunner.AfterTestClass from)
        {
            for (RunListener listener : listeners)
            {
                from.notifier.removeListener(listener);
            }
            return null;
        }
    }
}
