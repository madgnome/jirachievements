package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.Category;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserAchievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class AchievementDaoServiceTest extends ReferencableDaoServiceTest<Achievement, AchievementDaoService>
{
  private UserWrapperDaoService userWrapperDaoService;
  private UserAchievementDaoService userAchievementDaoService;
  private ActiveObjects ao;

  @Before
  public void setUp() throws Exception
  {
    ao = createActiveObjects();
    referencableDaoService = new AchievementDaoService(ao);
    userWrapperDaoService = new UserWrapperDaoService(ao);
    userAchievementDaoService = new UserAchievementDaoService(ao);
  }

  @Test
  public void activateFalseShouldDesactivates() throws Exception
  {
    final Achievement achievement = referencableDaoService.create("Achievement1");
    
    referencableDaoService.activate(achievement.getID(), false);
    
    assertThat(referencableDaoService.get("Achievement1").isActive(), is(false));
  }

  @Test
  public void activateUnknownIdShouldDoNothing() throws Exception
  {
    referencableDaoService.create("Achievement1");

    referencableDaoService.activate(-1, false);
  }

  @Test
  public void allActiveShouldReturnEmptyListIfAllInactive()
  {
    referencableDaoService.activate(referencableDaoService.create("Achievement1").getID(), false);
    referencableDaoService.activate(referencableDaoService.create("Achievement2").getID(), false);
    referencableDaoService.activate(referencableDaoService.create("Achievement3").getID(), false);
    referencableDaoService.activate(referencableDaoService.create("Achievement4").getID(), false);

    final List<Achievement> achievements = referencableDaoService.allActive();

    assertThat(achievements.size(), is(0));
  }

  @Test
  public void allActiveShouldReturnActiveAchievement()
  {
    referencableDaoService.activate(referencableDaoService.create("Achievement1").getID(), true);
    referencableDaoService.activate(referencableDaoService.create("Achievement2").getID(), false);
    referencableDaoService.activate(referencableDaoService.create("Achievement3").getID(), false);
    referencableDaoService.activate(referencableDaoService.create("Achievement4").getID(), false);

    final List<Achievement> achievements = referencableDaoService.allActive();

    assertThat(achievements.size(), is(1));
    assertThat(achievements.get(0).getRef(), is("Achievement1"));
  }

  @Test
  public void getUserNewAchievementsShouldReturnsNewAchivement()
  {
    UserWrapper userWrapper = userWrapperDaoService.create("user", null);
    final Achievement achievement = referencableDaoService.create("Achivement1");
    userAchievementDaoService.addAchievementToUser(achievement, userWrapper);

    final List<Achievement> newAchievements = referencableDaoService.getUserNewAchievements(userWrapper);
    final UserAchievement userAchievement = userAchievementDaoService.get(newAchievements.get(0), userWrapper);

    assertThat(newAchievements.size(), is(1));
    assertThat(userAchievement.isNotified(), is(false));
  }

  @Test
  public void allGroupByCategoryNameShouldReturnAchievementsByCategoryName() throws Exception
  {
    createAchievement("AchievementUser1", Category.USER);
    createAchievement("AchievementUser2", Category.USER);
    createAchievement("AchievementTester1", Category.TESTER);

    final Map<String,List<Achievement>> achievementsByCategoryName = referencableDaoService.allGroupByCategoryName();
    
    assertThat(achievementsByCategoryName.size(), is(2)); // User & Tester
    assertThat(achievementsByCategoryName.get(Category.TESTER.toString()).size(), is(1));
    assertThat(achievementsByCategoryName.get(Category.TESTER.toString()).get(0).getRef(), is("AchievementTester1"));
    assertThat(achievementsByCategoryName.get(Category.USER.toString()).size(), is(2));
  }

  @Test
  public void deleteAllShouldDeleteAllAchievements() throws Exception
  {
    createAchievement("AchievementUser1", Category.USER);
    createAchievement("AchievementUser2", Category.USER);
    createAchievement("AchievementTester1", Category.TESTER);

    assertEquals(referencableDaoService.all().size(), 3);
    referencableDaoService.deleteAll();
    assertEquals(referencableDaoService.all().size(), 0);
  }

  private void createAchievement(String ref, Category category)
  {
    final Achievement achievement = referencableDaoService.create(ref);
    achievement.setCategory(category);
    achievement.save();
  }
}
