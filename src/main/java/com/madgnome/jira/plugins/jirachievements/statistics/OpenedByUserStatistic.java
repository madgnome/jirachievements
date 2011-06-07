package com.madgnome.jira.plugins.jirachievements.statistics;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.changehistory.ChangeHistoryManager;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.jql.parser.JqlParseException;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.user.util.UserUtil;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.bean.ProjectComponentKey;
import com.madgnome.jira.plugins.jirachievements.data.bean.ProjectVersionKey;
import com.madgnome.jira.plugins.jirachievements.services.StatisticManager;
import com.madgnome.jira.plugins.jirachievements.services.UserManager;
import com.madgnome.jira.plugins.jirachievements.utils.data.IssueSearcher;
import gnu.trove.TObjectIntHashMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenedByUserStatistic extends AbstractStatisticCalculator
{

  public OpenedByUserStatistic(IssueSearcher issueSearcher, UserUtil userUtil, ChangeHistoryManager changeHistoryManager, StatisticManager statisticManager, UserManager userManager)
  {
    super(issueSearcher, userUtil, changeHistoryManager, statisticManager, userManager);
  }

  @Override
  protected StatisticRefEnum getStatisticRef()
  {
    return StatisticRefEnum.CREATED_ISSUE_COUNT;
  }

  @Override
  public void calculate() throws SearchException, JqlParseException
  {
    TObjectIntHashMap<String> resolvedByUser = new TObjectIntHashMap<String>();
    Map<String, TObjectIntHashMap<String>> resolvedByUserByProject = new HashMap<String, TObjectIntHashMap<String>>();
    Map<ProjectComponentKey, TObjectIntHashMap<String>> resolvedByUserByComponent = new HashMap<ProjectComponentKey, TObjectIntHashMap<String>>();
    Map<ProjectVersionKey, TObjectIntHashMap<String>> resolvedByUserByVersion = new HashMap<ProjectVersionKey, TObjectIntHashMap<String>>();

    List<Issue> matchingIssues = searchIssuesMatchingQuery();
    for (Issue issue : matchingIssues)
    {
      Project project = issue.getProjectObject();
      User reporterUser = issue.getReporterUser();
      if (reporterUser == null)
      {
        continue;
      }
      
      String user = reporterUser.getName();
      updateUserStatistic(resolvedByUser, user);
      updateProjectStatistic(resolvedByUserByProject, project, user);
      updateComponentsStatistic(resolvedByUserByComponent, project, issue.getComponentObjects(), user);
      updateVersionsStatistic(resolvedByUserByVersion, project, issue.getAffectedVersions(), user);
    }

    saveStatisticsByUser(resolvedByUser);
    saveStatisticsByProject(resolvedByUserByProject);
    saveStatisticsByComponent(resolvedByUserByComponent);
    saveStatisticsByVersion(resolvedByUserByVersion);
  }

  private List<Issue> searchIssuesMatchingQuery() throws JqlParseException, SearchException
  {
    return issueSearcher.searchIssues("");
  }

}
