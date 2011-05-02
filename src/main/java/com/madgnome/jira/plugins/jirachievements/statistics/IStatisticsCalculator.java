package com.madgnome.jira.plugins.jirachievements.statistics;

import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.jql.parser.JqlParseException;

public interface IStatisticsCalculator
{
  void calculateAll() throws SearchException, JqlParseException;
}
