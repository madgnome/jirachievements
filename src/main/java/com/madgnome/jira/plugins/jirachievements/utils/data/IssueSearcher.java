package com.madgnome.jira.plugins.jirachievements.utils.data;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.issue.search.SearchResults;
import com.atlassian.jira.jql.parser.JqlParseException;
import com.atlassian.jira.jql.parser.JqlQueryParser;
import com.atlassian.jira.user.util.UserUtil;
import com.atlassian.jira.web.bean.PagerFilter;
import com.atlassian.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class IssueSearcher
{
  private Logger logger = LoggerFactory.getLogger(IssueSearcher.class);

  private final JqlQueryParser jqlQueryParser;
  private final UserUtil userUtil;
  private final SearchService searchService;

  public IssueSearcher(JqlQueryParser jqlQueryParser, UserUtil userUtil, SearchService searchService)
  {
    this.jqlQueryParser = jqlQueryParser;
    this.userUtil = userUtil;
    this.searchService = searchService;
  }

  public List<Issue> searchIssues(String jql)
  {
    Query query = null;
    try
    {
      query = jqlQueryParser.parseQuery(jql);
      SearchResults searchResults = searchService.search(retrieveAdministrator(), query, PagerFilter.getUnlimitedFilter());

      return searchResults.getIssues();
    }
    catch (JqlParseException e)
    {
      logger.error("Can't search issue, jql parsing error:", e);
    }
    catch (SearchException e)
    {
      logger.error("Can't search issue:", e);
    }

    return Collections.emptyList();
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
