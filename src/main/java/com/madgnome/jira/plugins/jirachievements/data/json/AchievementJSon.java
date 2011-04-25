package com.madgnome.jira.plugins.jirachievements.data.json;

import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.Category;
import com.madgnome.jira.plugins.jirachievements.data.ao.Difficulty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class AchievementJSon
{
  private int id;
  private String ref;
  private String name;
  private String catchPhrase;
  private String description;
  private Category category;
  private Difficulty difficulty;
  private boolean hidden;

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

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

  public Difficulty getDifficulty()
  {
    return difficulty;
  }

  public void setDifficulty(Difficulty difficulty)
  {
    this.difficulty = difficulty;
  }

  public boolean isHidden()
  {
    return hidden;
  }

  public void isHidden(boolean hidden)
  {
    this.hidden = hidden;
  }

  public static AchievementJSon fromAchievement(Achievement achievement)
  {
    AchievementJSon achievementJSon = new AchievementJSon();
    achievementJSon.setId(achievement.getID());
    achievementJSon.setRef(achievement.getRef());
    achievementJSon.setName(achievement.getName());
    achievementJSon.setCatchPhrase(achievement.getCatchPhrase());
    achievementJSon.setDescription(achievement.getDescription());
    achievementJSon.setCategory(achievement.getCategory());
    achievementJSon.setDifficulty(achievement.getDifficulty());
    achievementJSon.isHidden(achievement.isHidden());

    return achievementJSon;
  }
}