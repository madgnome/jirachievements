package com.madgnome.jira.plugins.jirachievements.pageobjects;

import com.atlassian.jira.pageobjects.components.MultiSelect;
import com.atlassian.jira.pageobjects.pages.AbstractJiraAdminPage;
import com.atlassian.pageobjects.binder.Init;
import com.atlassian.pageobjects.elements.ElementBy;
import com.atlassian.pageobjects.elements.PageElement;
import com.atlassian.pageobjects.elements.query.TimedCondition;
import org.openqa.selenium.By;

public class AdvancedConfigPage extends AbstractJiraAdminPage
{
  private final static String URI = "/secure/admin/ViewJIRAHeroAdvancedConfiguration.jspa";
  private final static String LINK_ID = "jh-advanced-config-webfragment";

  @ElementBy(className = "admin-active-area")
  private PageElement configContainer;

  @ElementBy(name = "Update")
  private PageElement updateButton;

  private MultiSelect userStatusesSelect;
  private MultiSelect developerStatusesSelect;
  private MultiSelect testerStatusesSelect;

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

  @Init
  public void initialize()
  {
    userStatusesSelect = findMultiSelect("userStatuses");
    developerStatusesSelect = findMultiSelect("developerStatuses");
    testerStatusesSelect = findMultiSelect("testerStatuses");
  }

  public AdvancedConfigPage submit()
  {
    updateButton.click();
    return this;
  }

  public String getSelectedUserStatuses()
  {
    return getFirstItemText(userStatusesSelect);
  }

  public String getSelectedDeveloperStatuses()
  {
    return getFirstItemText(developerStatusesSelect);
  }

  public String getSelectedTesterStatuses()
  {
    return getFirstItemText(testerStatusesSelect);
  }

  public AdvancedConfigPage setUserStatuses(final String status)
  {
    setStatus(userStatusesSelect, status);
    return this;
  }

  public AdvancedConfigPage setDeveloperStatuses(final String status)
  {
    setStatus(developerStatusesSelect, status);
    return this;
  }

  public AdvancedConfigPage setTesterStatuses(final String status)
  {
    setStatus(testerStatusesSelect, status);
    return this;
  }

  private MultiSelect findMultiSelect(String id)
  {
    return elementFinder.find(By.id(id + "-multi-select")).isPresent() ?
        pageBinder.bind(MultiSelect.class, id) : null;
  }

  private void setStatus(MultiSelect multiSelect, String status)
  {
    multiSelect.clear();
    multiSelect.add(status);
  }

  private String getFirstItemText(MultiSelect multiSelect)
  {
    return multiSelect.getItems().get(0).getName();
  }
}
