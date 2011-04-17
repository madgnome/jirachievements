package com.madgnome.jira.plugins.jirachievements.listeners;

import com.atlassian.event.api.EventPublisher;
import com.atlassian.plugin.event.PluginEventListener;
import com.atlassian.plugin.event.events.PluginEnabledEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class JirachievementPluginEventListener implements InitializingBean, DisposableBean
{
  private static final Logger logger = LoggerFactory.getLogger(JirachievementPluginEventListener.class);

  private final EventPublisher eventPublisher;

  public JirachievementPluginEventListener(EventPublisher eventPublisher)
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

  @PluginEventListener
  public void onEvent(PluginEnabledEvent pluginEnabledEvent)
  {
    logger.info("Plugin enabled : %s", pluginEnabledEvent.getPlugin().getName());
  }
}
