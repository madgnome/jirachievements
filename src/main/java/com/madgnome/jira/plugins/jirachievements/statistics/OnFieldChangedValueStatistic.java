package com.madgnome.jira.plugins.jirachievements.statistics;

import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.changehistory.ChangeHistoryItem;
import com.atlassian.jira.issue.changehistory.ChangeHistoryManager;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.issue.search.SearchResults;
import com.atlassian.jira.jql.parser.JqlParseException;
import com.atlassian.jira.jql.parser.JqlQueryParser;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.user.util.UserUtil;
import com.atlassian.jira.web.bean.PagerFilter;
import com.atlassian.query.Query;
import com.madgnome.jira.plugins.jirachievements.data.services.IProjectStatisticDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserStatisticDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;
import gnu.trove.TObjectIntHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class OnFieldChangedValueStatistic extends AbstractStatisticCalculator
{
  private final static Logger logger = LoggerFactory.getLogger(OnFieldChangedValueStatistic.class);

  public OnFieldChangedValueStatistic(SearchService searchService, UserUtil userUtil, JqlQueryParser jqlQueryParser, ChangeHistoryManager changeHistoryManager, IUserStatisticDaoService userStatisticDaoService, IUserWrapperDaoService userWrapperDaoService, IProjectStatisticDaoService projectStatisticDaoService)
  {
    super(searchService, userUtil, jqlQueryParser, changeHistoryManager, userStatisticDaoService, userWrapperDaoService, projectStatisticDaoService);
  }

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

      List<ChangeHistoryItem> changeHistoryItems = changeHistoryManager.getAllChangeItems(issue);
      for (ChangeHistoryItem changeHistoryItem : changeHistoryItems)
      {
        if (changeHistoryItem.getField().equals(getFieldName()) &&
            changeHistoryItem.getTo().equals(getFieldValue()))
        {
          String user = changeHistoryItem.getUser();
          resolvedByUser.adjustOrPutValue(user, 1, 1);
          resolvedByUserForProject.adjustOrPutValue(user, 1, 1);

          break;
        }
      }
    }

    saveStatisticsByUser(resolvedByUser);
    saveStatisticsByProject(resolvedByUserByProject);
  }

  protected abstract String getFieldValue();
  protected abstract String getFieldName();
  protected abstract String getJQLQuery();


  private List<Issue> searchIssuesMatchingQuery() throws JqlParseException, SearchException
  {
    Query query = jqlQueryParser.parseQuery(getJQLQuery());
    SearchResults searchResults = searchService.search(retrieveAdministrator(), query, PagerFilter.getUnlimitedFilter());

    return searchResults.getIssues();
  }
}
