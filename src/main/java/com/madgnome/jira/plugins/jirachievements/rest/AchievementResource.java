package com.madgnome.jira.plugins.jirachievements.rest;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserAchievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.bean.AchievementBean;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/achievements")
public class AchievementResource
{
  private final JiraAuthenticationContext jiraAuthenticationContext;
  private final IUserWrapperDaoService userWrapperDaoService;
  private final IUserAchievementDaoService userAchievementDaoService;

  public AchievementResource(JiraAuthenticationContext jiraAuthenticationContext, IUserWrapperDaoService userWrapperDaoService, IUserAchievementDaoService userAchievementDaoService)
  {
    this.jiraAuthenticationContext = jiraAuthenticationContext;
    this.userWrapperDaoService = userWrapperDaoService;
    this.userAchievementDaoService = userAchievementDaoService;
  }

  @GET
  @Produces({MediaType.APPLICATION_JSON})
  public Response getUserAchievements()
  {
    User user = jiraAuthenticationContext.getLoggedInUser();
    UserWrapper userWrapper = userWrapperDaoService.getUserWrapper(user);

    List<AchievementBean> achievements = new ArrayList<AchievementBean>();

    // TODO Change this
    for (Achievement achievement : userWrapper.getNewAchievements())
    {
      achievements.add(AchievementBean.fromAchievement(achievement));
    }

    return Response.ok(achievements).build();
  }

  @PUT
  @Path("{id}")
  public Response updateUserAchievementStatus(@PathParam("id") int achievementId,
                                              @FormParam("notified") boolean notified)
  {
    User user = jiraAuthenticationContext.getLoggedInUser();
    UserWrapper userWrapper = userWrapperDaoService.getUserWrapper(user);

    UserAchievement userAchievement = userAchievementDaoService.get(achievementId, userWrapper.getID());
    userAchievement.setNotified(notified);
    userAchievement.save();

    return Response.ok().build();
  }
}
