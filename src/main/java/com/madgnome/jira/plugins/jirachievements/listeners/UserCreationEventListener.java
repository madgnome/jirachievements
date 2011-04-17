package com.madgnome.jira.plugins.jirachievements.listeners;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.event.user.UserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class UserCreationEventListener implements InitializingBean, DisposableBean
{
  private static final Logger logger = LoggerFactory.getLogger(UserCreationEventListener.class);

  private final EventPublisher eventPublisher;

  public UserCreationEventListener(EventPublisher eventPublisher)
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
  public void onUserEvent(UserEvent userEvent)
  {
    logger.info("User Event: %s", userEvent.getUser().getName());
  }
}
