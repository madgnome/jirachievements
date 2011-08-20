package com.madgnome.jira.plugins.jirachievements.statistics;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.bc.project.component.ProjectComponent;
import com.atlassian.jira.issue.changehistory.ChangeHistoryManager;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.version.Version;
import com.atlassian.jira.user.util.UserUtil;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.bean.ProjectComponentKey;
import com.madgnome.jira.plugins.jirachievements.data.bean.ProjectVersionKey;
import com.madgnome.jira.plugins.jirachievements.services.StatisticManager;
import com.madgnome.jira.plugins.jirachievements.services.UserManager;
import com.madgnome.jira.plugins.jirachievements.services.WorkflowConfiguration;
import com.madgnome.jira.plugins.jirachievements.utils.data.IssueSearcher;
import gnu.trove.TObjectIntHashMap;
import gnu.trove.TObjectIntProcedure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

public abstract class AbstractStatisticCalculator implements IStatisticCalculator
{
  protected final static Logger logger = LoggerFactory.getLogger(AbstractStatisticCalculator.class);

  protected final IssueSearcher issueSearcher;
  protected final UserUtil userUtil;
  protected final ChangeHistoryManager changeHistoryManager;

  protected final UserManager userManager;
  protected final WorkflowConfiguration workflowConfiguration;

  protected final StatisticManager statisticManager;

  public AbstractStatisticCalculator(IssueSearcher issueSearcher, UserUtil userUtil, ChangeHistoryManager changeHistoryManager, StatisticManager statisticManager, UserManager userManager, WorkflowConfiguration workflowConfiguration)
  {
    this.issueSearcher = issueSearcher;
    this.userUtil = userUtil;
    this.changeHistoryManager = changeHistoryManager;
    this.statisticManager = statisticManager;
    this.userManager = userManager;
    this.workflowConfiguration = workflowConfiguration;
  }

  protected abstract StatisticRefEnum getStatisticRef();

  protected User retrieveAdministrator()
  {
    for (User user : userUtil.getJiraAdministrators())
    {
      return user;
    }

    return null;
  }

  protected void saveStatisticsByProject(Map<String, TObjectIntHashMap<String>> resolvedByUserByProject)
  {
    for (final Map.Entry<String, TObjectIntHashMap<String>> kvp : resolvedByUserByProject.entrySet())
    {
      kvp.getValue().forEachEntry(new TObjectIntProcedure<String>()
      {
        @Override
        public boolean execute(String userName, int count)
        {
          UserWrapper userWrapper = userManager.get(userName);
          if (userWrapper != null)
          {
            statisticManager.createOrUpdateProjectStatistic(userWrapper, kvp.getKey(), getStatisticRef(), count);
          }
          else
          {
            logger.warn("Couldn't retrieve userwrapper for username '{}'", userName);
          }

          return true;
        }
      });
    }
  }

  protected void saveStatisticsByUser(TObjectIntHashMap<String> resolvedByUser)
  {
    resolvedByUser.forEachEntry(new TObjectIntProcedure<String>()
    {
      @Override
      public boolean execute(String userName, int count)
      {
        UserWrapper userWrapper = userManager.get(userName);
        if (userWrapper != null)
        {
          statisticManager.createOrUpdateUserStatistic(getStatisticRef(), userWrapper, count);
        }
        else
        {
          logger.warn("Couldn't retrieve userwrapper for username '{}'", userName);
        }

        return true;
      }
    });
  }

  protected void saveStatisticsByComponent(Map<ProjectComponentKey,TObjectIntHashMap<String>> resolvedByUserByComponent)
  {
    for (final Map.Entry<ProjectComponentKey, TObjectIntHashMap<String>> kvp : resolvedByUserByComponent.entrySet())
    {
      kvp.getValue().forEachEntry(new TObjectIntProcedure<String>()
      {
        @Override
        public boolean execute(String userName, int count)
        {
          UserWrapper userWrapper = userManager.get(userName);
          if (userWrapper != null)
          {
            ProjectComponentKey component = kvp.getKey();
            statisticManager.createOrUpdateComponentStatistic(userWrapper, component.getProject(), component.getComponent(), getStatisticRef(), count);
          }
          else
          {
            logger.warn("Couldn't retrieve userwrapper for username '{}'", userName);
          }

          return true;
        }
      });
    }
  }

  protected void saveStatisticsByVersion(Map<ProjectVersionKey,TObjectIntHashMap<String>> resolvedByUserByVersion)
  {
    for (final Map.Entry<ProjectVersionKey, TObjectIntHashMap<String>> kvp : resolvedByUserByVersion.entrySet())
    {
      kvp.getValue().forEachEntry(new TObjectIntProcedure<String>()
      {
        @Override
        public boolean execute(String userName, int count)
        {
          UserWrapper userWrapper = userManager.get(userName);
          if (userWrapper != null)
          {
            ProjectVersionKey version = kvp.getKey();
            statisticManager.createOrUpdateVersionStatistic(userWrapper, version.getProject(), version.getVersion(), getStatisticRef(), count);
          }
          else
          {
            logger.warn("Couldn't retrieve userwrapper for username '{}'", userName);
          }

          return true;
        }
      });
    }
  }

  protected void updateUserStatistic(TObjectIntHashMap<String> resolvedByUser, String user)
  {
    resolvedByUser.adjustOrPutValue(user, 1, 1);
  }

  protected void updateProjectStatistic(Map<String, TObjectIntHashMap<String>> resolvedByUserByProject, Project project, String user)
  {
    TObjectIntHashMap<String> resolvedByUserForProject = resolvedByUserByProject.get(project.getKey());
    if (resolvedByUserForProject == null)
    {
      resolvedByUserForProject = new TObjectIntHashMap<String>();
      resolvedByUserByProject.put(project.getKey(), resolvedByUserForProject);
    }

    resolvedByUserForProject.adjustOrPutValue(user, 1, 1);
  }

  protected void updateComponentsStatistic(Map<ProjectComponentKey, TObjectIntHashMap<String>> resolvedByUserByComponents,
                                         Project project,
                                         Collection<ProjectComponent> components,
                                         String user)
  {
    for (ProjectComponent component : components)
    {
      ProjectComponentKey componentKey = new ProjectComponentKey(project.getKey(), component.getName());
      TObjectIntHashMap<String> resolvedByUserForComponent = resolvedByUserByComponents.get(componentKey);
      if (resolvedByUserForComponent == null)
      {
        resolvedByUserForComponent = new TObjectIntHashMap<String>();
        resolvedByUserByComponents.put(componentKey, resolvedByUserForComponent);
      }

      resolvedByUserForComponent.adjustOrPutValue(user, 1, 1);
    }
  }

  protected void updateVersionsStatistic(Map<ProjectVersionKey, TObjectIntHashMap<String>> resolvedByUserByVersion,
                                       Project project,
                                       Collection<Version> versions,
                                       String user)
  {
    for (Version version : versions)
    {
      ProjectVersionKey versionKey = new ProjectVersionKey(project.getKey(), version.getName());
      TObjectIntHashMap<String> resolvedByUserForVersion = resolvedByUserByVersion.get(versionKey);
      if (resolvedByUserForVersion == null)
      {
        resolvedByUserForVersion = new TObjectIntHashMap<String>();
        resolvedByUserByVersion.put(versionKey, resolvedByUserForVersion);
      }

      resolvedByUserForVersion.adjustOrPutValue(user, 1, 1);
    }
  }
}
