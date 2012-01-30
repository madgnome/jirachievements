package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.madgnome.jira.plugins.jirachievements.data.ao.Config;
import com.madgnome.jira.plugins.jirachievements.data.ao.ConfigRefEnum;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ConfigDaoServiceTest extends ReferencableDaoServiceTest<Config, ConfigDaoService>
{
  @Before
  public void setUp() throws Exception
  {
    final ActiveObjects ao = createActiveObjects();
    referencableDaoService = new ConfigDaoService(ao);
  }

  @Test
  public void getOrCreateShouldCreateWithInitialValueIfAny() throws Exception
  {
    referencableDaoService.getOrCreate(ConfigRefEnum.ACHIEVEMENTS_REFRESH_RATE, "1");

    assertThat(referencableDaoService.get(ConfigRefEnum.ACHIEVEMENTS_REFRESH_RATE).getValue(), is("1"));
  }

  @Test
  public void getOrCreateShouldNotCreateNorModifyValueIfExist() throws Exception
  {
    referencableDaoService.getOrCreate(ConfigRefEnum.ACHIEVEMENTS_REFRESH_RATE, "1");

    referencableDaoService.getOrCreate(ConfigRefEnum.ACHIEVEMENTS_REFRESH_RATE, "2");

    assertThat(referencableDaoService.get(ConfigRefEnum.ACHIEVEMENTS_REFRESH_RATE).getValue(), is("1"));
  }

  @Test
  public void setValueShouldModifyValue() throws Exception
  {
    referencableDaoService.getOrCreate(ConfigRefEnum.ACHIEVEMENTS_REFRESH_RATE, "1");

    referencableDaoService.setValue(ConfigRefEnum.ACHIEVEMENTS_REFRESH_RATE, "2");

    assertThat(referencableDaoService.get(ConfigRefEnum.ACHIEVEMENTS_REFRESH_RATE).getValue(), is("2"));
  }
}
