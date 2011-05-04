package com.madgnome.jira.plugins.jirachievements.rest;

import com.madgnome.jira.plugins.jirachievements.rules.RulesChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/rules")
public class RuleResource
{
  private final Logger logger = LoggerFactory.getLogger(RuleResource.class);
  private final RulesChecker rulesChecker;

  public RuleResource(RulesChecker rulesChecker)
  {
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
