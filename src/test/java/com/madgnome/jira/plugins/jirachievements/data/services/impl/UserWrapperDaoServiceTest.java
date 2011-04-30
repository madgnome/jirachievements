package com.madgnome.jira.plugins.jirachievements.data.services.impl;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.crowd.embedded.impl.ImmutableUser;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;
import net.java.ao.sql.ActiveObjectSqlException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserWrapperDaoServiceTest extends AbstractServiceTest
{
  private IUserWrapperDaoService userWrapperDaoService;

  @Before
  public void setUp() throws Exception
  {
    userWrapperDaoService = new UserWrapperDaoService(createActiveObjects());
  }

  @Test
  public void createShouldCreateAndReturnUserWrapper() throws Exception
  {
    String name = "bob";
    User user = new ImmutableUser(0, name, "Sponge Bob", null, true);
    UserWrapper userWrapper = userWrapperDaoService.create(user);
    assertNotNull(userWrapper);
    entityManager.flushAll();

    UserWrapper newUserWrapper = userWrapperDaoService.get(name);
    assertNotNull(newUserWrapper);
    assertEquals(name, newUserWrapper.getJiraUserName());
  }

  @Test(expected = ActiveObjectSqlException.class)
  public void shouldNotCreateTwoUserWrapperWithSameName()
  {
    String name = "bob";
    User user = new ImmutableUser(0, name, "Sponge Bob", null, true);
    userWrapperDaoService.create(user);
    entityManager.flushAll();
    
    userWrapperDaoService.create(user);
    assertEquals(2, userWrapperDaoService.all().size());
  }

  @Test
  public void getShouldReturnNullIfAnyStatisticWithRef()
  {
    assertNull(userWrapperDaoService.get("bob"));
  }

  @Test
  public void getShouldReturnUserWrapperWithName()
  {
    String name = "bob";
    User user = new ImmutableUser(0, name, "Sponge Bob", null, true);
    userWrapperDaoService.create(user);
    entityManager.flushAll();

    UserWrapper userWrapper = userWrapperDaoService.get(name);
    assertNotNull(userWrapper);
    assertEquals(name, userWrapper.getJiraUserName());
  }

  @Test
  public void getOrCreateShouldCreateIfAny()
  {
    String name = "bob";
    User user = new ImmutableUser(0, name, "Sponge Bob", null, true);
    assertNull(userWrapperDaoService.get(user));
    UserWrapper userWrapper = userWrapperDaoService.getOrCreate(user);
    assertNotNull(userWrapper);
    entityManager.flushAll();

    assertNotNull(userWrapperDaoService.get(name));
  }

  @Test
  public void getOrCreateShouldReturnIfRefExists()
  {
    String name = "bob";
    User user = new ImmutableUser(0, name, "Sponge Bob", null, true);
    UserWrapper existingWrapper = userWrapperDaoService.create(user);

    UserWrapper userWrapper = userWrapperDaoService.getOrCreate(user);
    assertNotNull(userWrapper);
    assertEquals(existingWrapper, userWrapper);
  }

  @Test
  public void getOrCreateShouldCreateFirstThenReturn()
  {
    String name = "bob";
    User user = new ImmutableUser(0, name, "Sponge Bob", null, true);
    assertNull(userWrapperDaoService.get(user));
    UserWrapper existingUserWrapper = userWrapperDaoService.getOrCreate(user);
    assertNotNull(existingUserWrapper);

    UserWrapper userWrapper = userWrapperDaoService.getOrCreate(user);
    assertNotNull(userWrapper);
    assertEquals(existingUserWrapper, userWrapper);
  }
}
