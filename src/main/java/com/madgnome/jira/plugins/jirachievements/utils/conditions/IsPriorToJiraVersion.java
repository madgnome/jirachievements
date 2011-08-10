package com.madgnome.jira.plugins.jirachievements.utils.conditions;

import com.atlassian.plugin.PluginParseException;
import com.atlassian.plugin.web.Condition;
import com.atlassian.sal.api.ApplicationProperties;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IsPriorToJiraVersion implements Condition
{
  private Integer maxMajorVersion;
  private Integer maxMinorVersion;
  private Integer majorVersion;
  private Integer minorVersion;

  public IsPriorToJiraVersion(ApplicationProperties applicationProperties)
  {
    String versionString = applicationProperties.getVersion();
    String versionRegex = "^(\\d+)\\.(\\d+)";
    Pattern versionPattern = Pattern.compile(versionRegex);
    Matcher versionMatcher = versionPattern.matcher(versionString);
    versionMatcher.find();
    majorVersion = Integer.decode(versionMatcher.group(1));
    minorVersion = Integer.decode(versionMatcher.group(2));
  }

  public void init(final Map<String, String> paramMap) throws PluginParseException
  {
    maxMajorVersion = Integer.decode(paramMap.get("majorVersion"));
    maxMinorVersion = Integer.decode(paramMap.get("minorVersion"));
  }

  public boolean shouldDisplay(final Map<String, Object> context)
  {
    return (majorVersion < maxMajorVersion) || (majorVersion == maxMajorVersion) && (minorVersion < maxMinorVersion);
  }
}
