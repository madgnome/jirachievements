package com.madgnome.jira.plugins.jirachievements.listeners;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.event.type.EventType;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.rules.IRuleEngine;
import com.madgnome.jira.plugins.jirachievements.services.LevelManager;
import com.madgnome.jira.plugins.jirachievements.services.StatisticManager;
import com.madgnome.jira.plugins.jirachievements.services.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class IssueAchievementsEventListener implements InitializingBean, DisposableBean
{
  private static final Logger log = LoggerFactory.getLogger(IssueAchievementsEventListener.class);

  private final EventPublisher eventPublisher;
  private final IRuleEngine<IssueEvent> issueEventRuleEngine;
  private final StatisticManager statisticManager;
  private final UserManager userManager;
  private final LevelManager levelManager;

  public IssueAchievementsEventListener(EventPublisher eventPublisher, IRuleEngine<IssueEvent> issueEventRuleEngine, StatisticManager statisticManager, UserManager userManager, LevelManager levelManager)
  {
    this.eventPublisher = eventPublisher;
    this.issueEventRuleEngine = issueEventRuleEngine;
    this.statisticManager = statisticManager;
    this.userManager = userManager;
    this.levelManager = levelManager;
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

    final Long eventTypeId = issueEvent.getEventTypeId();
    final UserWrapper userWrapper = userManager.get(issueEvent.getUser());
    if (eventTypeId.equals(EventType.ISSUE_CREATED_ID))
    {
      statisticManager.incrementUserStatistic(StatisticRefEnum.CREATED_ISSUE_COUNT, userWrapper, 1);
    }
    else if (eventTypeId.equals(EventType.ISSUE_RESOLVED_ID))
    {
      statisticManager.incrementUserStatistic(StatisticRefEnum.CREATED_ISSUE_COUNT, userWrapper, 1);
    }
    else if (eventTypeId.equals(EventType.ISSUE_CLOSED_ID))
    {
      statisticManager.incrementUserStatistic(StatisticRefEnum.CREATED_ISSUE_COUNT, userWrapper, 1);
    }

    levelManager.checkLevels(userWrapper);
  }
}
