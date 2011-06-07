package com.madgnome.jira.plugins.jirachievements.rest;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.security.PermissionManager;
import com.atlassian.jira.security.Permissions;
import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.bean.AchievementBean;
import com.madgnome.jira.plugins.jirachievements.services.AchievementManager;
import com.madgnome.jira.plugins.jirachievements.services.UserManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/achievements")
public class AchievementResource extends AbstractBaseResource
{
  public AchievementResource(JiraAuthenticationContext jiraAuthenticationContext, PermissionManager permissionManager, UserManager userManager, AchievementManager achievementManager)
  {
    super(jiraAuthenticationContext, permissionManager, userManager, achievementManager);
  }

  @GET
  @Produces({MediaType.APPLICATION_JSON})
  public Response getUserAchievements()
  {
    UserWrapper userWrapper = userManager.getCurrentUserWrapper();

    List<AchievementBean> achievements = new ArrayList<AchievementBean>();

    // TODO Change this
    for (Achievement achievement : userWrapper.getNewAchievements())
    {
      achievements.add(AchievementBean.fromAchievement(achievement));
      break;
    }

    return Response.ok(achievements).build();
  }

  @PUT
  @Path("{id}")
  public Response updateUserAchievementStatus(@PathParam("id") int achievementId,
                                              @FormParam("notified") boolean notified)
  {
    UserWrapper userWrapper = userManager.getCurrentUserWrapper();

    achievementManager.updateNotification(achievementId, userWrapper, notified);

    return Response.ok().build();
  }

  @PUT
  @Path("{id}/config")
  public Response activeAchievement(@PathParam("id") int achievementId,
                                    @FormParam("active") boolean active)
  {
    User user = jiraAuthenticationContext.getLoggedInUser();
    if (!permissionManager.hasPermission(Permissions.ADMINISTER, user))
    {
      return Response.serverError().status(Response.Status.FORBIDDEN).build();
    }

    achievementManager.activate(achievementId, active);
    return Response.ok().build();
  }
}
