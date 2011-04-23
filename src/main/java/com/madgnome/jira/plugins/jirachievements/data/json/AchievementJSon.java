package com.madgnome.jira.plugins.jirachievements.data.json;

import com.madgnome.jira.plugins.jirachievements.data.ao.AchievementLevel;
import com.madgnome.jira.plugins.jirachievements.data.ao.Category;

public class AchievementJSon
{
  private String ref;
  private String name;
  private String catchPhrase;
  private String description;
  private Category category;
  private AchievementLevel level;
  private boolean hidden;

  public String getRef()
  {
    return ref;
  }

  public void setRef(String ref)
  {
    this.ref = ref;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getCatchPhrase()
  {
    return catchPhrase;
  }

  public void setCatchPhrase(String catchPhrase)
  {
    this.catchPhrase = catchPhrase;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public Category getCategory()
  {
    return category;
  }

  public void setCategory(Category category)
  {
    this.category = category;
  }

  public AchievementLevel getLevel()
  {
    return level;
  }

  public void setLevel(AchievementLevel achievementLevel)
  {
    this.level = achievementLevel;
  }

  public boolean isHidden()
  {
    return hidden;
  }

  public void setHidden(boolean hidden)
  {
    this.hidden = hidden;
  }
}