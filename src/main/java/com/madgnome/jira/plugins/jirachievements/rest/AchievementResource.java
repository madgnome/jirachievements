package com.madgnome.jira.plugins.jirachievements.rest;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("achievement")
public class AchievementResource
{
  private final JiraAuthenticationContext jiraAuthenticationContext;
  private final IUserWrapperDaoService userWrapperDaoService;

  public AchievementResource(JiraAuthenticationContext jiraAuthenticationContext, IUserWrapperDaoService userWrapperDaoService)
  {
    this.jiraAuthenticationContext = jiraAuthenticationContext;
    this.userWrapperDaoService = userWrapperDaoService;
  }

  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("/user")
  public Response getUserAchievements()
  {
    User user = jiraAuthenticationContext.getLoggedInUser();
    UserWrapper userWrapper = userWrapperDaoService.getUserWrapper(user);

    List<Map<String, String>> achievements = new ArrayList<Map<String, String>>();

    for (Achievement achievement : userWrapper.getAchievements())
    {
      Map<String, String> achievementMap = new HashMap<String, String>();
      achievementMap.put("ref", achievement.getRef());
      achievementMap.put("name", achievement.getName());
      achievementMap.put("description", achievement.getDescription());
      achievements.add(achievementMap);
    }

    return Response.ok(achievements).build();
  }
}
