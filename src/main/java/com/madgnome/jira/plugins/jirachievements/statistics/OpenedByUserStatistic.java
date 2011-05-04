package com.madgnome.jira.plugins.jirachievements.statistics;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.changehistory.ChangeHistoryManager;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.jql.parser.JqlParseException;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.user.util.UserUtil;
import com.madgnome.jira.plugins.jirachievements.data.ao.StatisticRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.services.IProjectStatisticDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserStatisticDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;
import com.madgnome.jira.plugins.jirachievements.utils.data.IssueSearcher;
import gnu.trove.TObjectIntHashMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenedByUserStatistic extends AbstractStatisticCalculator
{

  public OpenedByUserStatistic(IssueSearcher issueSearcher, UserUtil userUtil, ChangeHistoryManager changeHistoryManager, IUserWrapperDaoService userWrapperDaoService, IUserStatisticDaoService userStatisticDaoService, IProjectStatisticDaoService projectStatisticDaoService)
  {
    super(issueSearcher, userUtil, changeHistoryManager, userWrapperDaoService, userStatisticDaoService, projectStatisticDaoService);
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

    List<Issue> matchingIssues = searchIssuesMatchingQuery();
    for (Issue issue : matchingIssues)
    {
      Project project = issue.getProjectObject();
      TObjectIntHashMap<String> resolvedByUserForProject = resolvedByUserByProject.get(project.getKey());
      if (resolvedByUserForProject == null)
      {
        resolvedByUserForProject = new TObjectIntHashMap<String>();
        resolvedByUserByProject.put(project.getKey(), resolvedByUserForProject);
      }

      String user = issue.getReporterUser().getName();
      resolvedByUser.adjustOrPutValue(user, 1, 1);
      resolvedByUserForProject.adjustOrPutValue(user, 1, 1);
    }

    saveStatisticsByUser(resolvedByUser);
    saveStatisticsByProject(resolvedByUserByProject);
  }

  private List<Issue> searchIssuesMatchingQuery() throws JqlParseException, SearchException
  {
    return issueSearcher.searchIssues("");
  }
}
