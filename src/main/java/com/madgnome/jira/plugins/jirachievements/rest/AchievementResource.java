package com.madgnome.jira.plugins.jirachievements.rest;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserDaoService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("achievement")
public class AchievementResource
{
  private final JiraAuthenticationContext jiraAuthenticationContext;
  private final IUserDaoService userDaoService;

  public AchievementResource(JiraAuthenticationContext jiraAuthenticationContext, IUserDaoService userDaoService)
  {
    this.jiraAuthenticationContext = jiraAuthenticationContext;
    this.userDaoService = userDaoService;
  }

  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("/user")
  public Response getUserAchievements()
  {
    User user = jiraAuthenticationContext.getLoggedInUser();
    UserWrapper userWrapper = userDaoService.getUserWrapper(user);

    return Response.ok(userWrapper.getAchievements()).build();
  }
}
