package com.madgnome.jira.plugins.jirachievements.data.bean;

public class ProjectComponentKey
{
  private final String project;
  private final String component;

  public String getProject()
  {
    return project;
  }

  public String getComponent()
  {
    return component;
  }

  public ProjectComponentKey(String project, String component)
  {
    this.project = project;
    this.component = component;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ProjectComponentKey that = (ProjectComponentKey) o;

    if (!project.equals(that.project)) return false;
    if (!component.equals(that.component)) return false;

    return true;
  }

  @Override
  public int hashCode()
  {
    int result = project.hashCode();
    result = 31 * result + component.hashCode();
    return result;
  }
}
