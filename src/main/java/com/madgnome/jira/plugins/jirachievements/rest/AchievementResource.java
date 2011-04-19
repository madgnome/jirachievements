package com.madgnome.jira.plugins.jirachievements.rest;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserAchievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    List<Map<String, String>> achievements = new ArrayList<Map<String, String>>();

    for (Achievement achievement : userWrapper.getNewAchievements())
    {
      Map<String, String> achievementMap = new HashMap<String, String>();
      achievementMap.put("id", Integer.toString(achievement.getID()));
      achievementMap.put("ref", achievement.getRef());
      achievementMap.put("name", achievement.getName());
      achievementMap.put("description", achievement.getDescription());
      achievements.add(achievementMap);
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
