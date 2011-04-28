package com.madgnome.jira.plugins.jirachievements.ui;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.avatar.Avatar;
import com.atlassian.jira.avatar.AvatarService;
import com.atlassian.jira.plugin.projectpanel.impl.AbstractProjectTabPanel;
import com.atlassian.jira.project.browse.BrowseContext;
import com.atlassian.jira.util.collect.MapBuilder;
import com.madgnome.jira.plugins.jirachievements.data.ao.ProjectStatistic;
import com.madgnome.jira.plugins.jirachievements.data.services.IProjectStatisticDaoService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LeaderBoardProjectTabPanel extends AbstractProjectTabPanel
{
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

    List<Map<String, Object>> resolvedIssueLeaderBoard = retrieveResolvedIssueLeaderBoard(projectKey, ctx.getUser());
    params.put("resolvedIssueLB", resolvedIssueLeaderBoard);

    return params;
  }

  private List<Map<String, Object>> retrieveResolvedIssueLeaderBoard(String projectKey, User user)
  {
    List<ProjectStatistic> resolvedIssueCount = projectStatisticDaoService.findStatisticsForProjectAndRef(projectKey, "ResolvedIssueCount");

    // username
    // avatar url
    // Pour chaque stat
    //    count
    //    percentage (count / total)

    int total = 0;
    for (ProjectStatistic projectStatistic : resolvedIssueCount)
    {
      total += Integer.valueOf(projectStatistic.getValue());
    }

    List<Map<String, Object>> resolvedIssueLeaderBoard = new ArrayList<Map<String, Object>>();
    for (ProjectStatistic projectStatistic : resolvedIssueCount)
    {
      URI avatarUrl = avatarService.getAvatarURL(user, projectStatistic.getUserWrapper().getJiraUserName(), Avatar.Size.LARGE);

      Map<String,Object> resolvedIssueUserInfos =
              MapBuilder.<String, Object>newBuilder()
                      .add("username", projectStatistic.getUserWrapper().getJiraUserName())
                      .add("count", projectStatistic.getValue())
                      .add("percentage", Math.round(Double.valueOf(projectStatistic.getValue()) *100.0d / (double) total))
                      .add("avatarUrl", avatarUrl)
                      .toMap();

      resolvedIssueLeaderBoard.add(resolvedIssueUserInfos);

    }

    return resolvedIssueLeaderBoard;
  }
}
