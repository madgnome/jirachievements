package com.madgnome.jira.plugins.jirachievements.rest;

import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.security.PermissionManager;
import com.madgnome.jira.plugins.jirachievements.rules.RulesChecker;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/rules")
public class RuleResource extends AbstractBaseResource
{
  private final RulesChecker rulesChecker;

  public RuleResource(JiraAuthenticationContext jiraAuthenticationContext, PermissionManager permissionManager, RulesChecker rulesChecker)
  {
    super(jiraAuthenticationContext, permissionManager);
    this.rulesChecker = rulesChecker;
  }

  @GET
  @Path("/check")
  public Response check()
  {
    rulesChecker.check();

    return Response.ok().build();
  }
}
