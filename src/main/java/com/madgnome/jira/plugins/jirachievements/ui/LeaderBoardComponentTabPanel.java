package com.madgnome.jira.plugins.jirachievements.ui;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.avatar.Avatar;
import com.atlassian.jira.avatar.AvatarService;
import com.atlassian.jira.plugin.componentpanel.BrowseComponentContext;
import com.atlassian.jira.plugin.componentpanel.impl.GenericTabPanel;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.util.collect.MapBuilder;
import com.madgnome.jira.plugins.jirachievements.data.ao.ComponentStatistic;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.services.IProjectComponentStatisticDaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LeaderBoardComponentTabPanel extends GenericTabPanel
{
  private final static Logger logger = LoggerFactory.getLogger(LeaderBoardComponentTabPanel.class);
  private final IProjectComponentStatisticDaoService componentStatisticDaoService;
  private final AvatarService avatarService;

  public LeaderBoardComponentTabPanel(ProjectManager projectManager, JiraAuthenticationContext authenticationContext, IProjectComponentStatisticDaoService componentStatisticDaoService, AvatarService avatarService)
  {
    super(projectManager, authenticationContext);
    this.componentStatisticDaoService = componentStatisticDaoService;
    this.avatarService = avatarService;
  }

  @Override
  protected Map<String, Object> createVelocityParams(BrowseComponentContext ctx)
  {
    Map<String, Object> params = super.createVelocityParams(ctx);
    String projectKey = ctx.getProject().getKey();
    String component = ctx.getComponent().getName();
    User user = ctx.getUser();

    try
    {
      List<Map<String, Object>> createdIssueLeaderBoard = retrieveIssueLeaderBoard(projectKey, component, user, StatisticRefEnum.CREATED_ISSUE_COUNT);
      params.put("createdIssueLB", createdIssueLeaderBoard);
      List<Map<String, Object>> resolvedIssueLeaderBoard = retrieveIssueLeaderBoard(projectKey, component, user, StatisticRefEnum.RESOLVED_ISSUE_COUNT);
      params.put("resolvedIssueLB", resolvedIssueLeaderBoard);
      List<Map<String, Object>> testedIssueLeaderBoard = retrieveIssueLeaderBoard(projectKey, component, user, StatisticRefEnum.TESTED_ISSUE_COUNT);
      params.put("testedIssueLB", testedIssueLeaderBoard);
    }
    catch (Exception e)
    {
      logger.error("An error occured while retrieving leaderboard data", e);
      params.put("status", "ERROR");
    }

    return params;
  }

  private List<Map<String, Object>> retrieveIssueLeaderBoard(String projectKey, String component, User user, StatisticRefEnum statisticRefEnum)
  {
    List<ComponentStatistic> issueCounts = componentStatisticDaoService.findStatisticsForComponentAndRef(projectKey, component, statisticRefEnum);

    // username
    // avatar url
    // Pour chaque stat
    //    count
    //    percentage (count / total)

    int total = 0;
    for (ComponentStatistic componentStatistic : issueCounts)
    {
      total += componentStatistic.getValue();
    }

    List<Map<String, Object>> resolvedIssueLeaderBoard = new ArrayList<Map<String, Object>>();
    for (ComponentStatistic componentStatistic : issueCounts)
    {
      URI largeAvatarUrl = avatarService.getAvatarURL(user, componentStatistic.getUserWrapper().getJiraUserName(), Avatar.Size.LARGE);
      URI smallAvatarUrl = avatarService.getAvatarURL(user, componentStatistic.getUserWrapper().getJiraUserName(), Avatar.Size.SMALL);

      Map<String,Object> resolvedIssueUserInfos =
              MapBuilder.<String, Object>newBuilder()
                      .add("username", componentStatistic.getUserWrapper().getJiraUserName())
                      .add("count", componentStatistic.getValue())
                      .add("percentage", Math.round(componentStatistic.getValue() * 100.0d / (double) total))
                      .add("largeAvatarUrl", largeAvatarUrl)
                      .add("smallAvatarUrl", smallAvatarUrl)
                      .toMap();

      resolvedIssueLeaderBoard.add(resolvedIssueUserInfos);

    }

    return resolvedIssueLeaderBoard;
  }
}
