package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRef;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.ao.VersionStatistic;
import com.madgnome.jira.plugins.jirachievements.data.services.IProjectVersionStatisticDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IStatisticRefDaoService;
import net.java.ao.DBParam;
import net.java.ao.Query;

import java.util.Arrays;
import java.util.List;

public class ProjectVersionStatisticDaoService extends BaseDaoService<VersionStatistic> implements IProjectVersionStatisticDaoService
{
  private final IStatisticRefDaoService statisticRefDaoService;

  public ProjectVersionStatisticDaoService(ActiveObjects ao, IStatisticRefDaoService statisticRefDaoService)
  {
    super(ao);
    this.statisticRefDaoService = statisticRefDaoService;
  }

  @Override
  protected Class<VersionStatistic> getClazz()
  {
    return VersionStatistic.class;
  }

  @Override
  public VersionStatistic get(UserWrapper userWrapper, String projectKey, String component, StatisticRefEnum statisticRefEnum)
  {
    StatisticRef statisticRef = statisticRefDaoService.get(statisticRefEnum);
    if (statisticRef == null)
    {
      return null;
    }

    return getOrCreate(projectKey, component, statisticRef, userWrapper);
  }

  @Override
  public VersionStatistic createOrUpdate(UserWrapper userWrapper, String projectKey, String component, StatisticRefEnum statisticRefEnum, int value)
  {
    StatisticRef statisticRef = statisticRefDaoService.get(statisticRefEnum);

    VersionStatistic versionStatistic = null;
    if (statisticRef != null)
    {
      versionStatistic = getOrCreate(projectKey, component, statisticRef, userWrapper);
      versionStatistic.setValue(value);
      versionStatistic.save();
    }

    return versionStatistic;
  }

  @Override
  public List<VersionStatistic> findStatisticsForVersionAndRef(String projectKey, String component, StatisticRefEnum statisticRefEnum)
  {
    StatisticRef statRef = statisticRefDaoService.get(statisticRefEnum);
    Query query = Query.select().where("PROJECT_KEY = ? AND VERSION = ? AND STATISTIC_REF_ID = ?", projectKey, component, statRef.getID()).order("VALUE DESC");
    return Arrays.asList(ao.find(getClazz(), query));
  }

  private VersionStatistic getOrCreate(String projectKey, String version, StatisticRef statisticRef, UserWrapper userWrapper)
  {
    final VersionStatistic[] componentStatistics = ao.find(getClazz(), "STATISTIC_REF_ID = ? AND USER_WRAPPER_ID = ? AND PROJECT_KEY = ? AND VERSION = ?",
                                                                      statisticRef.getID(),
                                                                      userWrapper.getID(),
                                                                      projectKey,
                                                                      version);
    if (componentStatistics.length > 1)
    {
      throw new IllegalStateException("Found more than one statistic (" + componentStatistics.length + ") with ref " + statisticRef.getRef() + " for user " + userWrapper.getJiraUserName());
    }

    return componentStatistics.length != 0 ? componentStatistics[0] : getOrCreate(projectKey, version, statisticRef, userWrapper, 5);
  }

  private VersionStatistic getOrCreate(String projectKey, String version, StatisticRef statisticRef, UserWrapper userWrapper, int remainingTry)
  {
    VersionStatistic versionStatistic;
    try
    {
      versionStatistic = create(projectKey, version, statisticRef, userWrapper);
    }
    catch (Exception e)
    {
      if (remainingTry == 0)
      {
        throw new RuntimeException(String.format("Couldn't create VersionStatistic <%s> for project <%s>, version <%s> and user <%s>",
                statisticRef.getRef(), projectKey, version, userWrapper.getJiraUserName()), e);
      }

      return getOrCreate(projectKey, version, statisticRef, userWrapper, --remainingTry);
    }

    return versionStatistic;
  }

  private VersionStatistic create(String projectKey, String version, StatisticRef statisticRef, UserWrapper userWrapper)
  {
    VersionStatistic versionStatistic = ao.create(getClazz(), new DBParam("KEY", statisticRef.getID() + "|"  + projectKey + "|" + version + "|" + userWrapper.getID()));
    versionStatistic.setProjectKey(projectKey);
    versionStatistic.setVersion(version);
    versionStatistic.setStatisticRef(statisticRef);
    versionStatistic.setUserWrapper(userWrapper);
    versionStatistic.save();

    return versionStatistic;
  }

}
