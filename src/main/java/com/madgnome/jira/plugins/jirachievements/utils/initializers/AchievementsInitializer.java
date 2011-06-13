package com.madgnome.jira.plugins.jirachievements.utils.initializers;

import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.Category;
import com.madgnome.jira.plugins.jirachievements.data.ao.Difficulty;
import com.madgnome.jira.plugins.jirachievements.data.bean.AchievementBean;
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

public class AchievementsInitializer implements ITableInitializer
{
  private final static Logger logger = LoggerFactory.getLogger(AchievementsInitializer.class);
  private final IAchievementDaoService achievementDaoService;

  public AchievementsInitializer(IAchievementDaoService achievementDaoService)
  {
    this.achievementDaoService = achievementDaoService;
  }

  @Override
  public void initialize()
  {
    String achievementsString = loadAchievementsFile();
    AchievementBean[] achievements = parseAchievementsJSon(achievementsString);

    for (AchievementBean achievement : achievements)
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

  private AchievementBean[] parseAchievementsJSon(String json)
  {
    JSONUtils.getMorpherRegistry().registerMorpher( new EnumMorpher( Category.class ) );
    JSONUtils.getMorpherRegistry().registerMorpher( new EnumMorpher( Difficulty.class ) );
    JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(json);
    JsonConfig jsonConfig = new JsonConfig();
    jsonConfig.setArrayMode( JsonConfig.MODE_OBJECT_ARRAY );
    jsonConfig.setRootClass(AchievementBean.class);

    return (AchievementBean[]) JSONSerializer.toJava( jsonArray, jsonConfig );
  }

  private void createAchievement(AchievementBean achievementBean)
  {
    Achievement achievement = achievementDaoService.get(achievementBean.getRef());
    if (achievement == null)
    {
      achievement = achievementDaoService.create(achievementBean.getRef());
      achievement.setRef(achievementBean.getRef());
      achievement.setName(achievementBean.getName());
      achievement.setCatchPhrase(achievementBean.getCatchPhrase());
      achievement.setDescription(achievementBean.getDescription());
      achievement.setDifficulty(achievementBean.getDifficulty());
      achievement.setCategory(achievementBean.getCategory());
      achievement.setHidden(achievementBean.isHidden());
      achievement.save();
    }
  }
}
