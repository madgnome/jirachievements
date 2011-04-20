package com.madgnome.jira.plugins.jirachievements.listeners;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.event.issue.IssueEvent;
import com.madgnome.jira.plugins.jirachievements.rules.IRuleEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class IssueAchievementsEventListener implements InitializingBean, DisposableBean
{
  private static final Logger log = LoggerFactory.getLogger(IssueAchievementsEventListener.class);

  private final EventPublisher eventPublisher;
  private final IRuleEngine<IssueEvent> issueEventRuleEngine;

  public IssueAchievementsEventListener(EventPublisher eventPublisher, IRuleEngine<IssueEvent> issueEventRuleEngine)
  {
    this.eventPublisher = eventPublisher;
    this.issueEventRuleEngine = issueEventRuleEngine;
  }

  @Override
  public void afterPropertiesSet() throws Exception
  {
    eventPublisher.register(this);
  }

  @Override
  public void destroy() throws Exception
  {
    eventPublisher.unregister(this);
  }

  @EventListener
  public void onEvent(IssueEvent issueEvent)
  {
    issueEventRuleEngine.execute(issueEvent);
  }
}
