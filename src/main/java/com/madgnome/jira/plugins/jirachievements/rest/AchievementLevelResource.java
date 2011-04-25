package com.madgnome.jira.plugins.jirachievements.rest;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.madgnome.jira.plugins.jirachievements.data.ao.Difficulty;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/achievementlevels")
public class AchievementLevelResource
{
  private final JiraAuthenticationContext jiraAuthenticationContext;
  private final IUserWrapperDaoService userWrapperDaoService;
  private final IUserAchievementDaoService userAchievementDaoService;

  public AchievementLevelResource(JiraAuthenticationContext jiraAuthenticationContext, IUserWrapperDaoService userWrapperDaoService, IUserAchievementDaoService userAchievementDaoService)
  {
    this.jiraAuthenticationContext = jiraAuthenticationContext;
    this.userWrapperDaoService = userWrapperDaoService;
    this.userAchievementDaoService = userAchievementDaoService;
  }

  @GET
  @Path("/count")
  public Response getUserAchievementsCountByLevel()
  {
    User user = jiraAuthenticationContext.getLoggedInUser();
    UserWrapper userWrapper = userWrapperDaoService.getUserWrapper(user);

    Map<Difficulty, Integer> achievementsByLevel =  userAchievementDaoService.getAchievementsByLevel(userWrapper);

    return Response.ok(achievementsByLevel).build();
  }
}
