package com.madgnome.jira.plugins.jirachievements.utils.initializers;

import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.AchievementLevel;
import com.madgnome.jira.plugins.jirachievements.data.ao.Category;
import com.madgnome.jira.plugins.jirachievements.data.json.AchievementJSon;
import com.madgnome.jira.plugins.jirachievements.data.services.IAchievementDaoService;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.EnumMorpher;
import net.sf.json.util.JSONUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

public class AchievementsInitializer
{
  private final static Logger logger = LoggerFactory.getLogger(AchievementsInitializer.class);
  private final IAchievementDaoService achievementDaoService;

  public AchievementsInitializer(IAchievementDaoService achievementDaoService)
  {
    this.achievementDaoService = achievementDaoService;
  }

  public void initialize()
  {
    String achievementsString = loadAchievementsFile();
    AchievementJSon[] achievements = parseAchievementsJSon(achievementsString);

    for (AchievementJSon achievement : achievements)
    {
      createAchievement(achievement);
    }
  }

  private String loadAchievementsFile()
  {
    InputStream inputStream = getClass().getResourceAsStream("/data/achievements.json");

    StringWriter writer = new StringWriter();
    try
    {
      IOUtils.copy(inputStream, writer, "UTF-8");
    } catch (IOException e)
    {
      logger.error("Couldn't read the achievements file", e);
    }

    return writer.toString();
  }

  private AchievementJSon[] parseAchievementsJSon(String json)
  {
    JSONUtils.getMorpherRegistry().registerMorpher( new EnumMorpher( Category.class ) );
    JSONUtils.getMorpherRegistry().registerMorpher( new EnumMorpher( AchievementLevel.class ) );
    JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(json);
    JsonConfig jsonConfig = new JsonConfig();
    jsonConfig.setArrayMode( JsonConfig.MODE_OBJECT_ARRAY );
    jsonConfig.setRootClass(AchievementJSon.class);

    return (AchievementJSon[]) JSONSerializer.toJava( jsonArray, jsonConfig );
  }

  private void createAchievement(AchievementJSon achievementJSon)
  {
    Achievement achievement = achievementDaoService.getOrCreate(achievementJSon.getRef());
    achievement.setRef(achievementJSon.getRef());
    achievement.setName(achievementJSon.getName());
    achievement.setCatchPhrase(achievementJSon.getCatchPhrase());
    achievement.setDescription(achievementJSon.getDescription());
    achievement.setAchievementLevel(achievementJSon.getLevel());
    achievement.setCategory(achievementJSon.getCategory());
    achievement.setHidden(achievementJSon.isHidden());
    achievement.save();
  }
}
