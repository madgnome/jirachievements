package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.crowd.embedded.api.User;
import com.atlassian.crowd.embedded.impl.ImmutableUser;
import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.Difficulty;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserAchievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class UserAchievementDaoServiceTest extends BaseDaoServiceTest<UserAchievement, UserAchievementDaoService>
{
  private AchievementDaoService achievementDaoService;
  private UserWrapperDaoService userWrapperDaoService;

  @Before
  public void setUp()
  {
    ActiveObjects ao = createActiveObjects();
    daoService = new UserAchievementDaoService(ao);

    achievementDaoService = new AchievementDaoService(ao);
    userWrapperDaoService = new UserWrapperDaoService(ao);
  }

  @Test
  public void shouldAddAchievementToUser()
  {
    UserWrapper userWrapper = createUserWrapper();
    Achievement achievement = createAchievement("Achievement", Difficulty.BRONZE);
    assertEquals(0, userWrapper.getAchievements().length);
    
    daoService.addAchievementToUser(achievement, userWrapper);
    entityManager.flushAll();

    assertEquals(achievement, userWrapper.getAchievements()[0]);
  }

  @Test
  public void getShouldReturnNullIfAny()
  {
    UserWrapper userWrapper = createUserWrapper();
    Achievement achievement = createAchievement("Achievement", Difficulty.BRONZE);

    assertNull(daoService.get(achievement, userWrapper));
  }

  @Test
  public void getShouldReturnUserAchievement()
  {
    UserWrapper userWrapper = createUserWrapper();
    Achievement achievement = createAchievement("Achievement", Difficulty.BRONZE);

    daoService.addAchievementToUser(achievement, userWrapper);
    entityManager.flushAll();

    UserAchievement userAchievement = daoService.get(achievement, userWrapper);
    assertNotNull(userAchievement);
    assertEquals(achievement, userAchievement.getAchievement());
    assertEquals(userWrapper, userAchievement.getUserWrapper());
  }

  @Test
  public void getAchievementsByLevelShouldReturnCountOfAchievementsByLevel()
  {
    UserWrapper userWrapper = createUserWrapper();
    Achievement redAchievement = createAchievement("RedAchievement1", Difficulty.RED);
    Achievement bronzeAchievement1 = createAchievement("BronzeAchievement1", Difficulty.BRONZE);
    Achievement bronzeAchievement2 = createAchievement("BronzeAchievement2", Difficulty.BRONZE);
    Achievement silverAchievement = createAchievement("SilverAchievement", Difficulty.SILVER);

    daoService.addAchievementToUser(redAchievement, userWrapper);
    daoService.addAchievementToUser(bronzeAchievement1, userWrapper);
    daoService.addAchievementToUser(bronzeAchievement2, userWrapper);
    daoService.addAchievementToUser(silverAchievement, userWrapper);

    entityManager.flushAll();
    Map<Difficulty, Integer> achievementsByLevel = daoService.getAchievementsByLevel(userWrapper);
    assertNotNull(achievementsByLevel);
    assertEquals(1, achievementsByLevel.get(Difficulty.RED).intValue());
    assertEquals(2, achievementsByLevel.get(Difficulty.BRONZE).intValue());
    assertEquals(1, achievementsByLevel.get(Difficulty.SILVER).intValue());
    assertEquals(0, achievementsByLevel.get(Difficulty.GOLD).intValue());
  }

  private Achievement createAchievement(String ref, Difficulty difficulty)
  {
    Achievement achievement = achievementDaoService.create(ref);
    achievement.setDifficulty(difficulty);
    achievement.save();

    return achievement;
  }

  private UserWrapper createUserWrapper()
  {
    User user = new ImmutableUser(0, "bob", "Sponge Bob", null, true);
    return userWrapperDaoService.create(user);
  }
}
