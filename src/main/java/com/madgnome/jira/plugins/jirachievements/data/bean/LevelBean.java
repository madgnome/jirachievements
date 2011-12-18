package com.madgnome.jira.plugins.jirachievements.data.bean;

import com.madgnome.jira.plugins.jirachievements.data.ao.Category;
import com.madgnome.jira.plugins.jirachievements.data.ao.Level;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class LevelBean
{
  private int number;
  private Category category;
  private int minThreshold;
  private int maxThreshold;

  public int getNumber()
  {
    return number;
  }

  public Category getCategory()
  {
    return category;
  }

  public int getMinThreshold()
  {
    return minThreshold;
  }

  public int getMaxThreshold()
  {
    return maxThreshold;
  }

  public static LevelBean fromLevel(Level level)
  {
    LevelBean levelBean = new LevelBean();
    levelBean.number = level.getLevelNumber();
    levelBean.category = level.getCategory();
    levelBean.minThreshold = level.getMinThreshold();
    levelBean.maxThreshold = level.getMaxThreshold();

    return levelBean;
  }
}
