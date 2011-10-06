package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.activeobjects.test.TestActiveObjects;
import com.atlassian.activeobjects.test.TestActiveObjectsFieldNameConverter;
import com.madgnome.jira.plugins.jirachievements.data.DatabaseProcessor;
import com.madgnome.jira.plugins.jirachievements.data.PluginActiveObjectsTableNameConverter;
import net.java.ao.EntityManager;
import net.java.ao.test.converters.NameConverters;
import net.java.ao.test.jdbc.Data;
import net.java.ao.test.jdbc.Hsql;
import net.java.ao.test.jdbc.Jdbc;
import net.java.ao.test.junit.ActiveObjectsJUnitRunner;
import org.junit.runner.RunWith;

@RunWith(ActiveObjectsJUnitRunner.class)
@Jdbc(Hsql.class) // DynamicJdbcConfiguration.class
@Data(DatabaseProcessor.class)
@NameConverters(table = PluginActiveObjectsTableNameConverter.class, field = TestActiveObjectsFieldNameConverter.class)
public abstract class AbstractServiceTest
{
  protected EntityManager entityManager;

  protected ActiveObjects createActiveObjects()
  {
    return new TestActiveObjects(entityManager);
  }
}
