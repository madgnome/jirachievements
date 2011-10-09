package com.madgnome.jira.plugins.jirachievements.pageobjects;

import com.atlassian.jira.pageobjects.pages.AbstractJiraAdminPage;
import com.atlassian.pageobjects.elements.ElementBy;
import com.atlassian.pageobjects.elements.PageElement;
import com.atlassian.pageobjects.elements.query.TimedCondition;

public class GlobalConfigPage extends AbstractJiraAdminPage
{
  private final static String URI = "/secure/admin/ViewJIRAHeroGlobalConfiguration.jspa";
  private final static String LINK_ID = "jh-config-webfragment";

  @ElementBy(className = "admin-active-area")
  private PageElement configContainer;

  @Override
  public String linkId()
  {
    return LINK_ID;
  }

  @Override
  public TimedCondition isAt()
  {
    return configContainer.timed().isPresent();
  }

  @Override
  public String getUrl()
  {
    return URI;
  }
}
