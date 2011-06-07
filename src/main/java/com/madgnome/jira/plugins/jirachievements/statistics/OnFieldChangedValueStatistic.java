package com.madgnome.jira.plugins.jirachievements.statistics;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.changehistory.ChangeHistoryItem;
import com.atlassian.jira.issue.changehistory.ChangeHistoryManager;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.jql.parser.JqlParseException;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.user.util.UserUtil;
import com.madgnome.jira.plugins.jirachievements.data.bean.ProjectComponentKey;
import com.madgnome.jira.plugins.jirachievements.data.bean.ProjectVersionKey;
import com.madgnome.jira.plugins.jirachievements.services.StatisticManager;
import com.madgnome.jira.plugins.jirachievements.services.UserManager;
import com.madgnome.jira.plugins.jirachievements.utils.data.IssueSearcher;
import gnu.trove.TObjectIntHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class OnFieldChangedValueStatistic extends AbstractStatisticCalculator
{
  private final static Logger logger = LoggerFactory.getLogger(OnFieldChangedValueStatistic.class);

  public OnFieldChangedValueStatistic(IssueSearcher issueSearcher, UserUtil userUtil, ChangeHistoryManager changeHistoryManager, StatisticManager statisticManager, UserManager userManager)
  {
    super(issueSearcher, userUtil, changeHistoryManager, statisticManager, userManager);
  }

  public void calculate() throws SearchException, JqlParseException
  {
    TObjectIntHashMap<String> resolvedByUser = new TObjectIntHashMap<String>();
    Map<String, TObjectIntHashMap<String>> resolvedByUserByProject = new HashMap<String, TObjectIntHashMap<String>>();
    Map<ProjectComponentKey, TObjectIntHashMap<String>> resolvedByUserByComponent = new HashMap<ProjectComponentKey, TObjectIntHashMap<String>>();
    Map<ProjectVersionKey, TObjectIntHashMap<String>> resolvedByUserByVersion = new HashMap<ProjectVersionKey, TObjectIntHashMap<String>>();

    List<Issue> matchingIssues = searchIssuesMatchingQuery();
    for (Issue issue : matchingIssues)
    {
      List<ChangeHistoryItem> changeHistoryItems = changeHistoryManager.getAllChangeItems(issue);
      for (ChangeHistoryItem changeHistoryItem : changeHistoryItems)
      {
        if (changeHistoryItem.getField().equals(getFieldName()) &&
            changeHistoryItem.getTo().equals(getFieldValue()))
        {
          Project project = issue.getProjectObject();
          String user = changeHistoryItem.getUser();

          updateUserStatistic(resolvedByUser, user);
          updateProjectStatistic(resolvedByUserByProject, project, user);
          updateComponentsStatistic(resolvedByUserByComponent, project, project.getProjectComponents(), user);
          updateVersionsStatistic(resolvedByUserByVersion, project, issue.getAffectedVersions(), user);
          break;
        }
      }
    }

    saveStatisticsByUser(resolvedByUser);
    saveStatisticsByProject(resolvedByUserByProject);
    saveStatisticsByComponent(resolvedByUserByComponent);
    saveStatisticsByVersion(resolvedByUserByVersion);
  }

  protected abstract String getFieldValue();
  protected abstract String getFieldName();
  protected abstract String getJQLQuery();


  private List<Issue> searchIssuesMatchingQuery() throws JqlParseException, SearchException
  {
    return issueSearcher.searchIssues(getJQLQuery());
  }
}
