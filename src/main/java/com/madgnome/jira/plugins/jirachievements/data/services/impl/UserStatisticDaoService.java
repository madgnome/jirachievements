package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRef;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserStatistic;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.IStatisticRefDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserStatisticDaoService;
import net.java.ao.DBParam;

public class UserStatisticDaoService extends BaseDaoService<UserStatistic> implements IUserStatisticDaoService
{
  private final IStatisticRefDaoService statisticRefDaoService;

  @Override
  protected Class<UserStatistic> getClazz()
  {
    return UserStatistic.class;
  }

  public UserStatisticDaoService(ActiveObjects ao, IStatisticRefDaoService statisticRefDaoService)
  {
    super(ao);
    this.statisticRefDaoService = statisticRefDaoService;
  }

  @Override
  public UserStatistic get(UserWrapper userWrapper, StatisticRefEnum statisticRefEnum)
  {
    StatisticRef statisticRef = statisticRefDaoService.get(statisticRefEnum);
    if (statisticRef == null)
    {
      return null;
    }

    return getOrCreate(statisticRef, userWrapper);
  }

  @Override
  public UserStatistic createOrUpdate(StatisticRefEnum statRef, UserWrapper userWrapper, int value)
  {
    StatisticRef statisticRef = statisticRefDaoService.get(statRef);

    UserStatistic userStatistic = null;
    if (statisticRef != null)
    {
      userStatistic = getOrCreate(statisticRef, userWrapper);
      userStatistic.setValue(value);
      userStatistic.save();
    }

    return userStatistic;
  }

  private UserStatistic getOrCreate(StatisticRef statisticRef, UserWrapper userWrapper)
  {
    final UserStatistic[] userStatistics = ao.find(getClazz(), "STATISTIC_REF_ID = ? AND USER_WRAPPER_ID = ?", statisticRef.getID(), userWrapper.getID());
    if (userStatistics.length > 1)
    {
      throw new IllegalStateException("Found more than one statistic (" + userStatistics.length + ") with ref " + statisticRef.getRef() + " for user " + userWrapper.getJiraUserName());
    }

    return userStatistics.length != 0 ? userStatistics[0] : getOrCreate(statisticRef, userWrapper, 5);
  }

  private UserStatistic getOrCreate(StatisticRef statisticRef, UserWrapper userWrapper, int remainingTry)
  {
    UserStatistic userStatistic;
    try
    {
      userStatistic = create(statisticRef, userWrapper);
    }
    catch (Exception e)
    {
      if (remainingTry == 0)
      {
        throw new RuntimeException(String.format("Couldn't create UserStatistic <%s> for user <%s>", statisticRef.getRef(), userWrapper.getJiraUserName()), e);
      }

      return getOrCreate(statisticRef, userWrapper, --remainingTry);
    }

    return userStatistic;
  }

  private UserStatistic create(StatisticRef statisticRef, UserWrapper userWrapper)
  {
    UserStatistic userStatistic = ao.create(UserStatistic.class, new DBParam("KEY", statisticRef.getID() + "|"  + userWrapper.getID()));
    userStatistic.setStatisticRef(statisticRef);
    userStatistic.setUserWrapper(userWrapper);
    userStatistic.save();

    return userStatistic;
  }
}
