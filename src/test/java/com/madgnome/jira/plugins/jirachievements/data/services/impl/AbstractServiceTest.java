package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.ao.ActiveObjectsFieldNameConverter;
import com.madgnome.jira.plugins.jirachievements.data.DatabaseProcessor;
import net.java.ao.EntityManager;
import net.java.ao.test.NameConverters;
import net.java.ao.test.jdbc.Data;
import net.java.ao.test.jdbc.Hsql;
import net.java.ao.test.jdbc.Jdbc;
import net.java.ao.test.junit.ActiveObjectsJUnitRunner;
import org.junit.runner.RunWith;

@RunWith(ActiveObjectsJUnitRunner.class)
@Jdbc(Hsql.class)
@Data(DatabaseProcessor.class)
@NameConverters(field = ActiveObjectsFieldNameConverter.class)
public abstract class AbstractServiceTest
{
  protected EntityManager entityManager;
}
