package com.madgnome.jira.plugins.jirachievements.pageobjects;

import com.atlassian.jira.pageobjects.pages.project.browseversion.BrowseVersionTab;
import com.atlassian.pageobjects.binder.Init;
import com.atlassian.pageobjects.elements.PageElement;
import com.atlassian.pageobjects.elements.PageElementFinder;
import com.atlassian.pageobjects.elements.query.TimedCondition;
import org.openqa.selenium.By;

import javax.inject.Inject;

import static com.atlassian.pageobjects.elements.query.Conditions.and;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public class AbstractVersionTab implements BrowseVersionTab
{
  protected final String linkId;
//  protected final String projectKey;
//  protected final String versionId;

  protected PageElement linkItem;
  protected PageElement link;

  @Inject
  private PageElementFinder elementFinder;

//  public AbstractVersionTab(String linkId, String projectKey, String versionId)
//  {
//    this.projectKey = projectKey;
//    this.versionId = versionId;
//    this.linkId = checkNotNull(linkId);
//  }


  public AbstractVersionTab(String linkId)
  {
    this.linkId  = checkNotNull(linkId);
  }

  @Init
  private void init()
  {
    for (PageElement item : elementFinder.find(By.cssSelector("ul.vertical.tabs")).findAll(By.tagName("li")))
    {
      final PageElement link = item.find(By.id(linkId));
      if (link.isPresent())
      {
        this.linkItem = item;
        this.link = link;
        break;
      }
    }
    checkState(link != null, "Not found link by ID " + linkId);
  }

  @Override
  public final String linkId()
  {
    return linkId;
  }

  @Override
  public final TimedCondition isOpen()
  {
    return and(linkItem.timed().hasClass("active"), linkItem.timed().hasClass("loaded"));
  }
}
