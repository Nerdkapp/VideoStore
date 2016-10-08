package com.nerdkapp.videorentalstore.infrastructure.user;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class InMemoryUserRepositoryTest
{
  Map<String, Integer> db = new HashMap<>();
  InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository(db);

  @Test
  public void add_user_points() throws Exception
  {
    inMemoryUserRepository.addBonusPoints("lcoccia", 3);

    assertEquals(new Integer(3), db.get("lcoccia"));
  }

  @Test
  public void retrieve_user_points() throws Exception
  {
    inMemoryUserRepository.addBonusPoints("lcoccia", 3);

    assertEquals(new Integer(3), inMemoryUserRepository.getPoints("lcoccia"));
  }

  @Test
  public void zero_points_for_new_users() throws Exception
  {
    assertEquals(new Integer(0), inMemoryUserRepository.getPoints("lcoccia"));
  }
}