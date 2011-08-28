package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.madgnome.jira.plugins.jirachievements.data.ao.ComponentStatistic;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRef;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.IProjectComponentStatisticDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IStatisticRefDaoService;
import net.java.ao.Query;

import java.util.Arrays;
import java.util.List;

public class ProjectComponentStatisticDaoService extends BaseDaoService<ComponentStatistic> implements IProjectComponentStatisticDaoService
{
  private final IStatisticRefDaoService statisticRefDaoService;

  public ProjectComponentStatisticDaoService(ActiveObjects ao, IStatisticRefDaoService statisticRefDaoService)
  {
    super(ao);
    this.statisticRefDaoService = statisticRefDaoService;
  }

  @Override
  protected Class<ComponentStatistic> getClazz()
  {
    return ComponentStatistic.class;
  }

  @Override
  public ComponentStatistic get(UserWrapper userWrapper, String projectKey, String component, StatisticRefEnum statisticRefEnum)
  {
    StatisticRef statisticRef = statisticRefDaoService.get(statisticRefEnum);
    if (statisticRef == null)
    {
      return null;
    }

    return getOrCreate(projectKey, component, statisticRef, userWrapper);
  }

  @Override
  public ComponentStatistic createOrUpdate(UserWrapper userWrapper, String projectKey, String component, StatisticRefEnum statisticRefEnum, int value)
  {
    StatisticRef statisticRef = statisticRefDaoService.get(statisticRefEnum);

    ComponentStatistic componentStatistic = null;
    if (statisticRef != null)
    {
      componentStatistic = getOrCreate(projectKey, component, statisticRef, userWrapper);
      componentStatistic.setValue(value);
      componentStatistic.save();
    }

    return componentStatistic;
  }

  @Override
  public List<ComponentStatistic> findStatisticsForComponentAndRef(String projectKey, String component, StatisticRefEnum statisticRefEnum)
  {
    StatisticRef statRef = statisticRefDaoService.get(statisticRefEnum);
    Query query = Query.select().where("PROJECT_KEY = ? AND COMPONENT = ? AND STATISTIC_REF_ID = ?", projectKey, component, statRef.getID()).order("VALUE DESC");
    return Arrays.asList(ao.find(getClazz(), query));
  }

  private ComponentStatistic getOrCreate(String projectKey, String component, StatisticRef statisticRef, UserWrapper userWrapper)
  {
    final ComponentStatistic[] componentStatistics = ao.find(getClazz(), "STATISTIC_REF_ID = ? AND USER_WRAPPER_ID = ? AND PROJECT_KEY = ? AND COMPONENT = ?",
                                                                      statisticRef.getID(),
                                                                      userWrapper.getID(),
                                                                      projectKey,
                                                                      component);
    if (componentStatistics.length > 1)
    {
      throw new IllegalStateException("Found more than one statistic (" + componentStatistics.length + ") with ref " + statisticRef.getRef() + " for user " + userWrapper.getJiraUserName());
    }

    return componentStatistics.length != 0 ? componentStatistics[0] : getOrCreate(projectKey, component, statisticRef, userWrapper, 5);
  }

  private ComponentStatistic getOrCreate(String projectKey, String component, StatisticRef statisticRef, UserWrapper userWrapper, int remainingTry)
  {
    ComponentStatistic componentStatistic;
    try
    {
      componentStatistic = create(projectKey, component, statisticRef, userWrapper);
    }
    catch (Exception e)
    {
      if (remainingTry == 0)
      {
        throw new RuntimeException(String.format("Couldn't create ProjectStatistic <%s> for project <%s>, component <%s> and user <%s>",
                statisticRef.getRef(), projectKey, component, userWrapper.getJiraUserName()), e);
      }

      return getOrCreate(projectKey, component, statisticRef, userWrapper, --remainingTry);
    }

    return componentStatistic;
  }

  private ComponentStatistic create(String projectKey, String component, StatisticRef statisticRef, UserWrapper userWrapper)
  {
    ComponentStatistic componentStatistic = ao.create(getClazz());
    componentStatistic.setProjectKey(projectKey);
    componentStatistic.setComponent(component);
    componentStatistic.setStatisticRef(statisticRef);
    componentStatistic.setUserWrapper(userWrapper);
    componentStatistic.save();

    return componentStatistic;
  }

}
