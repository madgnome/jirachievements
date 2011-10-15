package com.madgnome.jira.plugins.jirachievements.pageobjects;

import com.atlassian.pageobjects.elements.PageElement;
import org.openqa.selenium.By;

public class UserDetailsRow
{
  private final String username;
  private final int count;

  private final PageElement rowElement;

  public String getUsername()
  {
    return username;
  }

  public int getCount()
  {
    return count;
  }

  public UserDetailsRow(PageElement rowElement)
  {
    this.rowElement = rowElement;

    username = rowElement.find(By.className("name")).find(By.tagName("a")).getText();
    count = Integer.valueOf(rowElement.find(By.className("count")).getText());
  }
}
