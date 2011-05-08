package com.madgnome.jira.plugins.jirachievements.rest;

import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.security.PermissionManager;
import com.madgnome.jira.plugins.jirachievements.data.ao.Category;
import com.madgnome.jira.plugins.jirachievements.data.ao.Level;
import com.madgnome.jira.plugins.jirachievements.data.bean.LevelBean;
import com.madgnome.jira.plugins.jirachievements.data.services.ILevelDaoService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/levels")
public class LevelResource extends AbstractBaseResource
{
  private final ILevelDaoService levelDaoService;

  public LevelResource(JiraAuthenticationContext jiraAuthenticationContext, PermissionManager permissionManager, ILevelDaoService levelDaoService)
  {
    super(jiraAuthenticationContext, permissionManager);
    this.levelDaoService = levelDaoService;
  }


  @GET
  @Path("{category}")
  @Produces({MediaType.APPLICATION_JSON})
  public Response getCategoryLevels(@PathParam("category") String category)
  {
    List<Level> levels = levelDaoService.all(Category.valueOf(category.toUpperCase()));

    List<LevelBean> levelBeans = new ArrayList<LevelBean>(levels.size());
    for (Level level : levels)
    {
      levelBeans.add(LevelBean.fromLevel(level));
    }
    
    return Response.ok(levelBeans).build();
  }
}
