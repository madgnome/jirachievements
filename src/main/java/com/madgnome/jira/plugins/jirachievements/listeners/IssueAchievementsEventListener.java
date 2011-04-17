package com.madgnome.jira.plugins.jirachievements.listeners;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.event.type.EventType;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserStatistic;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.IAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserStatisticDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class IssueAchievementsEventListener implements InitializingBean, DisposableBean
{
  private static final Logger log = LoggerFactory.getLogger(IssueAchievementsEventListener.class);

  private final EventPublisher eventPublisher;
  private final JiraAuthenticationContext jiraAuthenticationContext;
  private final IUserWrapperDaoService userWrapperDaoService;
  private final IUserStatisticDaoService userStatisticDaoService;
  private final IUserAchievementDaoService userAchievementDaoService;
  private final IAchievementDaoService achievementDaoService;

  public IssueAchievementsEventListener(EventPublisher eventPublisher, JiraAuthenticationContext jiraAuthenticationContext, IUserWrapperDaoService userWrapperDaoService, IUserStatisticDaoService userStatisticDaoService, IUserAchievementDaoService userAchievementDaoService, IAchievementDaoService achievementDaoService)
  {
    this.eventPublisher = eventPublisher;
    this.jiraAuthenticationContext = jiraAuthenticationContext;
    this.userWrapperDaoService = userWrapperDaoService;
    this.userStatisticDaoService = userStatisticDaoService;
    this.userAchievementDaoService = userAchievementDaoService;
    this.achievementDaoService = achievementDaoService;
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
      User user = jiraAuthenticationContext.getLoggedInUser();
      UserWrapper userWrapper = userWrapperDaoService.getUserWrapper(user);
      UserStatistic userStatistic = userStatisticDaoService.getStatistic(userWrapper, "IssueCount");
      if (!"0".equals(userStatistic.getValue()))
      {
        Achievement achievement = achievementDaoService.getOrCreate("Padawan");
        userAchievementDaoService.addAchievementToUser(achievement, userWrapper);
      }
    }
    else
    {

    }
  }
}
