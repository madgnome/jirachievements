package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.ao.ActiveObjectsFieldNameConverter;
import com.atlassian.activeobjects.ao.ActiveObjectsTableNameConverter;
import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.activeobjects.test.TestActiveObjects;
import com.madgnome.jira.plugins.jirachievements.data.DatabaseProcessor;
import com.madgnome.jira.plugins.jirachievements.data.PluginActiveObjectsJUnitRunner;
import net.java.ao.EntityManager;
import net.java.ao.test.NameConverters;
import net.java.ao.test.jdbc.Data;
import net.java.ao.test.jdbc.Hsql;
import net.java.ao.test.jdbc.Jdbc;
import org.junit.runner.RunWith;

@RunWith(PluginActiveObjectsJUnitRunner.class)
@Jdbc(Hsql.class)
@Data(DatabaseProcessor.class)
@NameConverters(table = ActiveObjectsTableNameConverter.class, field = ActiveObjectsFieldNameConverter.class)
public abstract class AbstractServiceTest
{
  protected EntityManager entityManager;

  protected ActiveObjects createActiveObjects()
  {
    return new TestActiveObjects(entityManager);
  }
}
