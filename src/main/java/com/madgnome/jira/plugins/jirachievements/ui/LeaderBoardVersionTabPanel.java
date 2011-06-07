package com.madgnome.jira.plugins.jirachievements.ui;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.avatar.Avatar;
import com.atlassian.jira.avatar.AvatarService;
import com.atlassian.jira.issue.search.SearchProvider;
import com.atlassian.jira.plugin.versionpanel.BrowseVersionContext;
import com.atlassian.jira.plugin.versionpanel.impl.GenericTabPanel;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.util.collect.MapBuilder;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.ao.VersionStatistic;
import com.madgnome.jira.plugins.jirachievements.data.services.IProjectVersionStatisticDaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LeaderBoardVersionTabPanel extends GenericTabPanel
{
  private final static Logger logger = LoggerFactory.getLogger(LeaderBoardComponentTabPanel.class);
  private final IProjectVersionStatisticDaoService versionStatisticDaoService;
  private final AvatarService avatarService;

  public LeaderBoardVersionTabPanel(JiraAuthenticationContext authenticationContext, SearchProvider searchProvider, IProjectVersionStatisticDaoService versionStatisticDaoService, AvatarService avatarService)
  {
    super(authenticationContext, searchProvider);
    this.versionStatisticDaoService = versionStatisticDaoService;
    this.avatarService = avatarService;
  }

  @Override
  protected Map<String, Object> createVelocityParams(BrowseVersionContext ctx)
  {
    Map<String, Object> params = super.createVelocityParams(ctx);
    String projectKey = ctx.getProject().getKey();
    String version = ctx.getVersion().getName();
    com.opensymphony.user.User user = ctx.getUser();

    try
    {
      List<Map<String, Object>> createdIssueLeaderBoard = retrieveIssueLeaderBoard(projectKey, version, user, StatisticRefEnum.CREATED_ISSUE_COUNT);
      params.put("createdIssueLB", createdIssueLeaderBoard);
      List<Map<String, Object>> resolvedIssueLeaderBoard = retrieveIssueLeaderBoard(projectKey, version, user, StatisticRefEnum.RESOLVED_ISSUE_COUNT);
      params.put("resolvedIssueLB", resolvedIssueLeaderBoard);
      List<Map<String, Object>> testedIssueLeaderBoard = retrieveIssueLeaderBoard(projectKey, version, user, StatisticRefEnum.TESTED_ISSUE_COUNT);
      params.put("testedIssueLB", testedIssueLeaderBoard);
    }
    catch (Exception e)
    {
      logger.error("An error occured while retrieving leaderboard data", e);
      params.put("status", "ERROR");
    }

    return params;
  }

  private List<Map<String, Object>> retrieveIssueLeaderBoard(String projectKey, String version, User user, StatisticRefEnum statisticRefEnum)
  {
    List<VersionStatistic> issueCounts = versionStatisticDaoService.findStatisticsForVersionAndRef(projectKey, version, statisticRefEnum);

    // username
    // avatar url
    // Pour chaque stat
    //    count
    //    percentage (count / total)

    int total = 0;
    for (VersionStatistic versionStatistic : issueCounts)
    {
      total += versionStatistic.getValue();
    }

    List<Map<String, Object>> resolvedIssueLeaderBoard = new ArrayList<Map<String, Object>>();
    for (VersionStatistic versionStatistic : issueCounts)
    {
      URI largeAvatarUrl = avatarService.getAvatarURL(user, versionStatistic.getUserWrapper().getJiraUserName(), Avatar.Size.LARGE);
      URI smallAvatarUrl = avatarService.getAvatarURL(user, versionStatistic.getUserWrapper().getJiraUserName(), Avatar.Size.SMALL);

      Map<String,Object> resolvedIssueUserInfos =
              MapBuilder.<String, Object>newBuilder()
                      .add("username", versionStatistic.getUserWrapper().getJiraUserName())
                      .add("count", versionStatistic.getValue())
                      .add("percentage", Math.round(versionStatistic.getValue() * 100.0d / (double) total))
                      .add("largeAvatarUrl", largeAvatarUrl)
                      .add("smallAvatarUrl", smallAvatarUrl)
                      .toMap();

      resolvedIssueLeaderBoard.add(resolvedIssueUserInfos);

    }

    return resolvedIssueLeaderBoard;
  }
}
