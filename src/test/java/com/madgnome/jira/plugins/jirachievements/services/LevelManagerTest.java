package com.madgnome.jira.plugins.jirachievements.services;

import com.madgnome.jira.plugins.jirachievements.data.ao.*;
import com.madgnome.jira.plugins.jirachievements.data.services.ILevelDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserLevelDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserStatisticDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class LevelManagerTest
{
  @Test
  public void shouldAddThreeNewLevelsToUserIfFirstCall()
  {
    IUserStatisticDaoService userStatisticDaoService = mock(IUserStatisticDaoService.class);
    UserStatistic createdUserStatistic = mock(UserStatistic.class);
    when(createdUserStatistic.getValue()).thenReturn(10);
    when(userStatisticDaoService.get(any(UserWrapper.class), any(StatisticRefEnum.class))).thenReturn(createdUserStatistic);

    IUserWrapperDaoService userWrapperDaoService = mock(IUserWrapperDaoService.class);
    IUserLevelDaoService userLevelDaoService = mock(IUserLevelDaoService.class);

    ILevelDaoService levelDaoService = mock(ILevelDaoService.class);
    Level level = mock(Level.class);
    when(levelDaoService.findMatchingLevel(any(Category.class), anyInt())).thenReturn(level);

    LevelManager levelManager = new LevelManager(userStatisticDaoService, userWrapperDaoService, userLevelDaoService, levelDaoService);

    UserWrapper userWrapper = mock(UserWrapper.class);
    levelManager.checkLevels(userWrapper);

    verify(userLevelDaoService, times(3)).addLevelToUser(any(Level.class), any(UserWrapper.class));
  }

  @Test
  public void shouldNotAddlevelsToUserIfNothingChanged()
  {
    IUserStatisticDaoService userStatisticDaoService = mock(IUserStatisticDaoService.class);
    UserStatistic createdUserStatistic = mock(UserStatistic.class);
    when(createdUserStatistic.getValue()).thenReturn(10);
    when(userStatisticDaoService.get(any(UserWrapper.class), any(StatisticRefEnum.class))).thenReturn(createdUserStatistic);

    IUserWrapperDaoService userWrapperDaoService = mock(IUserWrapperDaoService.class);
    IUserLevelDaoService userLevelDaoService = mock(IUserLevelDaoService.class);

    ILevelDaoService levelDaoService = mock(ILevelDaoService.class);
    Level level = mock(Level.class);
    when(levelDaoService.findMatchingLevel(any(Category.class), anyInt())).thenReturn(level);

    LevelManager levelManager = new LevelManager(userStatisticDaoService, userWrapperDaoService, userLevelDaoService, levelDaoService);

    UserWrapper userWrapper = mock(UserWrapper.class);
    levelManager.checkLevels(userWrapper);
    when(userLevelDaoService.last(any(UserWrapper.class), any(Category.class))).thenReturn(level);

    levelManager.checkLevels(userWrapper);

    verify(userLevelDaoService, times(3)).addLevelToUser(any(Level.class), any(UserWrapper.class));
  }
}
