package com.madgnome.jira.plugins.jirachievements.ui;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.avatar.Avatar;
import com.atlassian.jira.avatar.AvatarService;
import com.atlassian.jira.plugin.projectpanel.impl.AbstractProjectTabPanel;
import com.atlassian.jira.project.browse.BrowseContext;
import com.atlassian.jira.util.collect.MapBuilder;
import com.madgnome.jira.plugins.jirachievements.data.ao.ProjectStatistic;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.services.IProjectStatisticDaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LeaderBoardProjectTabPanel extends AbstractProjectTabPanel
{
  private final static Logger logger = LoggerFactory.getLogger(LeaderBoardProjectTabPanel.class);
  private final IProjectStatisticDaoService projectStatisticDaoService;
  private final AvatarService avatarService;

  public LeaderBoardProjectTabPanel(IProjectStatisticDaoService projectStatisticDaoService, AvatarService avatarService)
  {
    this.projectStatisticDaoService = projectStatisticDaoService;
    this.avatarService = avatarService;
  }

  @Override
  public boolean showPanel(BrowseContext browseContext)
  {
    return true;
  }

  @Override
  public String getHtml(BrowseContext ctx)
  {
    return super.getHtml(ctx);
  }

  @Override
  protected Map<String, Object> createVelocityParams(BrowseContext ctx)
  {
    Map<String, Object> params = super.createVelocityParams(ctx);
    String projectKey = ctx.getProject().getKey();

    try
    {
      List<Map<String, Object>> createdIssueLeaderBoard = retrieveResolvedIssueLeaderBoard(projectKey, ctx.getUser(), StatisticRefEnum.CREATED_ISSUE_COUNT);
      params.put("createdIssueLB", createdIssueLeaderBoard);
      List<Map<String, Object>> resolvedIssueLeaderBoard = retrieveResolvedIssueLeaderBoard(projectKey, ctx.getUser(), StatisticRefEnum.RESOLVED_ISSUE_COUNT);
      params.put("resolvedIssueLB", resolvedIssueLeaderBoard);
      List<Map<String, Object>> testedIssueLeaderBoard = retrieveResolvedIssueLeaderBoard(projectKey, ctx.getUser(), StatisticRefEnum.TESTED_ISSUE_COUNT);
      params.put("testedIssueLB", testedIssueLeaderBoard);
    }
    catch (Exception e)
    {
      logger.error("An error occured while retrieving leaderboard data", e);
      params.put("status", "ERROR");
    }

    return params;
  }

  private List<Map<String, Object>> retrieveResolvedIssueLeaderBoard(String projectKey, User user, StatisticRefEnum statisticRefEnum)
  {
    List<ProjectStatistic> resolvedIssueCount = projectStatisticDaoService.findStatisticsForProjectAndRef(projectKey, statisticRefEnum);

    // username
    // avatar url
    // Pour chaque stat
    //    count
    //    percentage (count / total)

    int total = 0;
    for (ProjectStatistic projectStatistic : resolvedIssueCount)
    {
      total += projectStatistic.getValue();
    }

    List<Map<String, Object>> resolvedIssueLeaderBoard = new ArrayList<Map<String, Object>>();
    for (ProjectStatistic projectStatistic : resolvedIssueCount)
    {
      URI largeAvatarUrl = avatarService.getAvatarURL(user, projectStatistic.getUserWrapper().getJiraUserName(), Avatar.Size.LARGE);
      URI smallAvatarUrl = avatarService.getAvatarURL(user, projectStatistic.getUserWrapper().getJiraUserName(), Avatar.Size.SMALL);

      Map<String,Object> resolvedIssueUserInfos =
              MapBuilder.<String, Object>newBuilder()
                      .add("username", projectStatistic.getUserWrapper().getJiraUserName())
                      .add("count", projectStatistic.getValue())
                      .add("percentage", Math.round(projectStatistic.getValue() * 100.0d / (double) total))
                      .add("largeAvatarUrl", largeAvatarUrl)
                      .add("smallAvatarUrl", smallAvatarUrl)
                      .toMap();

      resolvedIssueLeaderBoard.add(resolvedIssueUserInfos);

    }

    return resolvedIssueLeaderBoard;
  }
}
