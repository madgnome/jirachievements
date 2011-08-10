package com.madgnome.jira.plugins.jirachievements.listeners;

import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.event.user.UserEvent;
import com.atlassian.jira.event.user.UserEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;

public class OldFashionUserEventListener implements UserEventListener, InitializingBean
{
  private static final Logger logger = LoggerFactory.getLogger(OldFashionUserEventListener.class);
  private final EventPublisher eventPublisher;

  public OldFashionUserEventListener(EventPublisher eventPublisher)
  {
    this.eventPublisher = eventPublisher;
  }

  @Override
  public void userSignup(UserEvent userEvent)
  {
    logger.info("User signup {}", userEvent.getUser().getName());
  }

  @Override
  public void userCreated(UserEvent userEvent)
  {
    logger.info("User created {}", userEvent.getUser().getName());
  }

  @Override
  public void userForgotPassword(UserEvent userEvent)
  {
  }

  @Override
  public void userForgotUsername(UserEvent userEvent)
  {
  }

  @Override
  public void userCannotChangePassword(UserEvent userEvent)
  {
  }

  @Override
  public void init(Map map)
  {
  }

  @Override
  public String[] getAcceptedParams()
  {
    return new String[0];
  }

  @Override
  public boolean isInternal()
  {
    return false;
  }

  @Override
  public boolean isUnique()
  {
    return false;
  }

  @Override
  public String getDescription()
  {
    return "Blablabla";
  }

  @Override
  public void afterPropertiesSet() throws Exception
  {
    eventPublisher.register(this);
  }
}
