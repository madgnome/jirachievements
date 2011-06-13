package com.madgnome.jira.plugins.jirachievements.utils.initializers;

import java.util.List;

public class TableInitializers
{
  private final List<ITableInitializer> tableInitializers;

  public TableInitializers(List<ITableInitializer> tableInitializers)
  {
    this.tableInitializers = tableInitializers;
  }

  public void initialize()
  {
    for (ITableInitializer tableInitializer : tableInitializers)
    {
      tableInitializer.initialize();
    }
  }
}
