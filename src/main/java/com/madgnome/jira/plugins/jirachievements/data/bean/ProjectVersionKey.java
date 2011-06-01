package com.madgnome.jira.plugins.jirachievements.data.bean;

public class ProjectVersionKey
{
  private final String project;
  private final String version;

  public String getProject()
  {
    return project;
  }

  public String getVersion()
  {
    return version;
  }

  public ProjectVersionKey(String project, String version)
  {
    this.project = project;
    this.version = version;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ProjectVersionKey that = (ProjectVersionKey) o;

    if (!project.equals(that.project)) return false;
    if (!version.equals(that.version)) return false;

    return true;
  }

  @Override
  public int hashCode()
  {
    int result = project.hashCode();
    result = 31 * result + version.hashCode();
    return result;
  }
}
