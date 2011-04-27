package com.madgnome.jira.plugins.jirachievements.statistics;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.changehistory.ChangeHistoryManager;
import com.atlassian.jira.issue.history.ChangeItemBean;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.issue.search.SearchResults;
import com.atlassian.jira.jql.parser.JqlParseException;
import com.atlassian.jira.jql.parser.JqlQueryParser;
import com.atlassian.jira.user.util.UserUtil;
import com.atlassian.jira.web.bean.PagerFilter;
import com.atlassian.query.Query;

import java.util.List;

public class ResolvedByUserStatistic
{
  private final JqlQueryParser jqlQueryParser;
  private final UserUtil userUtil;
  private final SearchService searchService;
  private final ChangeHistoryManager changeHistoryManager;

  public ResolvedByUserStatistic(SearchService searchService, UserUtil userUtil, JqlQueryParser jqlQueryParser, ChangeHistoryManager changeHistoryManager)
  {
    this.searchService = searchService;
    this.userUtil = userUtil;
    this.jqlQueryParser = jqlQueryParser;
    this.changeHistoryManager = changeHistoryManager;
  }

  public void calculate() throws SearchException, JqlParseException
  {
    Query query = jqlQueryParser.parseQuery("status WAS Resolved");

    SearchResults searchResults = searchService.search(retrieveAdministrator(), query, PagerFilter.getUnlimitedFilter());
    for (Issue issue : searchResults.getIssues())
    {
      List<ChangeItemBean> changeItemBeans = changeHistoryManager.getChangeItemsForField(issue, "status");
    }
  }

  private User retrieveAdministrator()
  {
    for (User user : userUtil.getJiraAdministrators())
    {
      return user;
    }

    return null;
  }
}
