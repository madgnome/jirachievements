package com.madgnome.jira.plugins.jirachievements.rest;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.security.PermissionManager;
import com.atlassian.jira.security.Permissions;
import com.madgnome.jira.plugins.jirachievements.data.services.IConfigDaoService;
import com.madgnome.jira.plugins.jirachievements.services.AchievementManager;
import com.madgnome.jira.plugins.jirachievements.services.UserManager;

import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/configs")
public class ConfigResource extends AbstractBaseResource
{
  private final IConfigDaoService configDaoService;

  public ConfigResource(JiraAuthenticationContext jiraAuthenticationContext, PermissionManager permissionManager, UserManager userManager, AchievementManager achievementManager, IConfigDaoService configDaoService)
  {
    super(jiraAuthenticationContext, permissionManager, userManager, achievementManager);
    this.configDaoService = configDaoService;
  }


  @PUT
  @Path("{ref}")
  public Response setValue(@PathParam("ref") String ref,
                           @FormParam("value") String value)
  {
    User user = jiraAuthenticationContext.getLoggedInUser();
    if (!permissionManager.hasPermission(Permissions.ADMINISTER, user))
    {
      return Response.status(Response.Status.FORBIDDEN).build();
    }
    
    configDaoService.setValue(ref, value);

    return Response.ok().build();
  }
}
