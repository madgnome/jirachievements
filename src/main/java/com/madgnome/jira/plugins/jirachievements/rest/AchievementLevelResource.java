package com.madgnome.jira.plugins.jirachievements.rest;

import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.security.PermissionManager;
import com.madgnome.jira.plugins.jirachievements.data.ao.Difficulty;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.services.AchievementManager;
import com.madgnome.jira.plugins.jirachievements.services.UserManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/achievementlevels")
public class AchievementLevelResource extends AbstractBaseResource
{
  private final IUserAchievementDaoService userAchievementDaoService;

  public AchievementLevelResource(JiraAuthenticationContext jiraAuthenticationContext, PermissionManager permissionManager, UserManager userManager, AchievementManager achievementManager, IUserAchievementDaoService userAchievementDaoService)
  {
    super(jiraAuthenticationContext, permissionManager, userManager, achievementManager);
    this.userAchievementDaoService = userAchievementDaoService;
  }

  @GET
  @Path("/count")
  public Response getUserAchievementsCountByLevel()
  {
    UserWrapper userWrapper = userManager.getCurrentUserWrapper();

    if (!userWrapper.isActive())
    {
      return Response.ok().build();
    }

    Map<Difficulty, Integer> achievementsByLevel =  userAchievementDaoService.getAchievementsByLevel(userWrapper);

    return Response.ok(achievementsByLevel).build();
  }
}
