package com.madgnome.jira.plugins.jirachievements.data.bean;

import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.Category;
import com.madgnome.jira.plugins.jirachievements.data.ao.Difficulty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class AchievementBean
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

  public void setHidden(boolean hidden)
  {
    this.hidden = hidden;
  }

  public static AchievementBean fromAchievement(Achievement achievement)
  {
    AchievementBean achievementBean = new AchievementBean();
    achievementBean.setId(achievement.getID());
    achievementBean.setRef(achievement.getRef());
    achievementBean.setName(achievement.getName());
    achievementBean.setCatchPhrase(achievement.getCatchPhrase());
    achievementBean.setDescription(achievement.getDescription());
    achievementBean.setCategory(achievement.getCategory());
    achievementBean.setDifficulty(achievement.getDifficulty());
    achievementBean.setHidden(achievement.isHidden());

    return achievementBean;
  }
}