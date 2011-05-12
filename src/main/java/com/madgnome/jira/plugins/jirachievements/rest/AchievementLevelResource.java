package com.madgnome.jira.plugins.jirachievements.rest;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.security.PermissionManager;
import com.madgnome.jira.plugins.jirachievements.data.ao.Difficulty;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/achievementlevels")
public class AchievementLevelResource extends AbstractBaseResource
{
  private final IUserWrapperDaoService userWrapperDaoService;
  private final IUserAchievementDaoService userAchievementDaoService;

  public AchievementLevelResource(JiraAuthenticationContext jiraAuthenticationContext, PermissionManager permissionManager, IUserAchievementDaoService userAchievementDaoService, IUserWrapperDaoService userWrapperDaoService)
  {
    super(jiraAuthenticationContext, permissionManager);
    this.userAchievementDaoService = userAchievementDaoService;
    this.userWrapperDaoService = userWrapperDaoService;
  }

  @GET
  @Path("/count")
  public Response getUserAchievementsCountByLevel()
  {
    User user = jiraAuthenticationContext.getLoggedInUser();
    UserWrapper userWrapper = userWrapperDaoService.get(user);

    if (!userWrapper.isActive())
    {
      return Response.ok().build();
    }

    Map<Difficulty, Integer> achievementsByLevel =  userAchievementDaoService.getAchievementsByLevel(userWrapper);

    return Response.ok(achievementsByLevel).build();
  }
}
