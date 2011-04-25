package com.madgnome.jira.plugins.jirachievements.ui;

import com.atlassian.jira.plugin.projectpanel.impl.AbstractProjectTabPanel;
import com.atlassian.jira.project.browse.BrowseContext;

import java.util.Map;

public class LeaderBoardProjectTabPanel extends AbstractProjectTabPanel
{
  @Override
  public boolean showPanel(BrowseContext browseContext)
  {
    return true;
  }

  @Override
  public String getHtml(BrowseContext ctx)
  {
    return super.getHtml(ctx);
  }

  @Override
  protected Map<String, Object> createVelocityParams(BrowseContext ctx)
  {
    Map<String, Object> params = super.createVelocityParams(ctx);

    return params;
  }
}
