package com.madgnome.jira.plugins.jirachievements.data.ao;

import net.java.ao.ManyToMany;
import net.java.ao.schema.Default;

public interface Achievement extends ReferencableEntity
{
  String getName();
  void setName(String name);

  String getCatchPhrase();
  void setCatchPhrase(String catchPhrase);

  String getDescription();
  void setDescription(String description);

  Category getCategory();
  void setCategory(Category category);

  Difficulty getDifficulty();
  void setDifficulty(Difficulty level);

  @Default("false")
  boolean isHidden();
  void setHidden(boolean hidden);

  @Default("true")
  boolean isActive();
  void setActive(boolean active);

  @ManyToMany(UserAchievement.class)
  UserWrapper[] getUsers();
}
