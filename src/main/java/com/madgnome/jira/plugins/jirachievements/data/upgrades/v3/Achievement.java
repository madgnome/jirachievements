package com.madgnome.jira.plugins.jirachievements.data.upgrades.v3;

import com.madgnome.jira.plugins.jirachievements.data.ao.AchievementImpl;
import com.madgnome.jira.plugins.jirachievements.data.ao.ReferencableEntity;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserAchievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import net.java.ao.Implementation;
import net.java.ao.ManyToMany;
import net.java.ao.Preload;
import net.java.ao.schema.Default;
import net.java.ao.schema.Ignore;

@Implementation(AchievementImpl.class)
@Preload({"DIFFICULTY"})
public interface Achievement extends ReferencableEntity
{
  String getName();
  void setName(String name);

  String getCatchPhrase();
  void setCatchPhrase(String catchPhrase);

  String getDescription();
  void setDescription(String description);

  String getCategory();
  void setCategory(String category);

  String getDifficulty();
  void setDifficulty(String level);

  @Ignore
  String getImageRef();

  @Default("false")
  boolean isHidden();
  void setHidden(boolean hidden);

  @Default("true")
  boolean isActive();
  void setActive(boolean active);

  @ManyToMany(UserAchievement.class)
  UserWrapper[] getUsers();
}
