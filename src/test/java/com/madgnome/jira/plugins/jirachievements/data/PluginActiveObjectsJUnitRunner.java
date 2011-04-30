package com.madgnome.jira.plugins.jirachievements.data;

import com.atlassian.activeobjects.internal.Prefix;
import com.atlassian.activeobjects.internal.SimplePrefix;
import com.madgnome.jira.plugins.jirachievements.utils.data.AOUtil;
import net.java.ao.schema.FieldNameConverter;
import net.java.ao.schema.TableNameConverter;
import net.java.ao.test.NameConverters;
import net.java.ao.test.jdbc.Hsql;
import net.java.ao.test.jdbc.Jdbc;
import net.java.ao.test.jdbc.JdbcConfiguration;
import net.java.ao.test.junit.ActiveObjectTransactionMethodRule;
import net.java.ao.test.lucene.WithIndex;
import org.junit.rules.MethodRule;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

public class PluginActiveObjectsJUnitRunner extends BlockJUnit4ClassRunner
{
  private final JdbcConfiguration jdbcConfiguration;
  private final boolean withIndex;
  private TableNameConverter tableNameConverter;
  private FieldNameConverter fieldNameConverter;

  public PluginActiveObjectsJUnitRunner(Class<?> klass) throws InitializationError
  {
    super(klass);
    jdbcConfiguration = resolveJdbcConfiguration(klass);
    tableNameConverter = tableNameConverter(klass);
    fieldNameConverter = fieldNameConverter(klass);
    withIndex = withIndex(klass);
  }

  @Override
  protected List<MethodRule> rules(Object test)
  {
    final LinkedList<MethodRule> methodRules = new LinkedList<MethodRule>(super.rules(test));
    methodRules.add(new ActiveObjectTransactionMethodRule(test, jdbcConfiguration, withIndex, tableNameConverter, fieldNameConverter));
    return methodRules;
  }

  private boolean withIndex(Class<?> klass)
  {
    return klass.isAnnotationPresent(WithIndex.class);
  }

  private TableNameConverter tableNameConverter(Class<?> klass)
  {
    if (klass.isAnnotationPresent(NameConverters.class))
    {
      Class<? extends TableNameConverter> tableNameConverterClass = klass.getAnnotation(NameConverters.class).table();
      try
      {
        Prefix prefix = new SimplePrefix(AOUtil.getTablePrefix(), "_");
        return tableNameConverterClass.getDeclaredConstructor(Prefix.class).newInstance(prefix);
      }
      catch (NoSuchMethodException e)
      {
        throw new RuntimeException(e);
      }
      catch (InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      catch (InstantiationException e)
      {
        throw new RuntimeException(e);
      }
      catch (IllegalAccessException e)
      {
        throw new RuntimeException(e);
      }
    }
    
    return null;
  }

  private FieldNameConverter fieldNameConverter(Class<?> klass)
  {
    if (klass.isAnnotationPresent(NameConverters.class))
    {
      return newInstance(klass.getAnnotation(NameConverters.class).field());
    }
    return null;
  }

  private JdbcConfiguration resolveJdbcConfiguration(Class<?> klass)
  {
    if (klass.isAnnotationPresent(Jdbc.class))
    {
      return newInstance(klass.getAnnotation(Jdbc.class).value());
    }
    return getDefaultJdbcConfiguration();
  }

  private <T> T newInstance(Class<T> type)
  {
    try
    {
      return type.newInstance();
    }
    catch (InstantiationException e)
    {
      throw new RuntimeException(e);
    }
    catch (IllegalAccessException e)
    {
      throw new RuntimeException(e);
    }
  }

  private JdbcConfiguration getDefaultJdbcConfiguration()
  {
    return new Hsql();
  }
}
