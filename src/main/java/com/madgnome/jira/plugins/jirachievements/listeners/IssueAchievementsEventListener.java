package com.madgnome.jira.plugins.jirachievements.listeners;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.event.type.EventType;
import com.atlassian.jira.issue.Issue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class IssueAchievementsEventListener implements InitializingBean, DisposableBean
{
  private static final Logger log = LoggerFactory.getLogger(IssueAchievementsEventListener.class);

  private final EventPublisher eventPublisher;

  public IssueAchievementsEventListener(EventPublisher eventPublisher)
  {
    this.eventPublisher = eventPublisher;
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
  public void onIssueEvent(IssueEvent issueEvent)
  {
    Long eventTypeId = issueEvent.getEventTypeId();
    Issue issue = issueEvent.getIssue();

    if (EventType.ISSUE_CREATED_ID.equals(eventTypeId))
    {

    }
    else
    {

    }
  }
}
