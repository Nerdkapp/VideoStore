package com.nerdkapp.videorentalstore.infrastructure.user;

import com.nerdkapp.videorentalstore.domain.user.UserRepository;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class UsersResourceTest
{
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  UserRepository userRepository = context.mock(UserRepository.class);
  UsersResource usersResource = new UsersResource(userRepository);

  @Test
  public void get_user_points() throws Exception
  {
    String user = "lcoccia";

    context.checking(new Expectations(){{
      oneOf(userRepository).getPoints(user);
      will(returnValue(1000));
    }});

    assertEquals(new Integer(1000), usersResource.getPoints(user).points);
  }
}