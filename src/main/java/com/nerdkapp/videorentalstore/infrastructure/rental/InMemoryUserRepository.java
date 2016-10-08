package com.nerdkapp.videorentalstore.infrastructure.rental;

import com.nerdkapp.videorentalstore.domain.UserRepository;

import java.util.Map;

public class InMemoryUserRepository implements UserRepository
{
  private Map<String, Integer> users;

  public InMemoryUserRepository(Map<String, Integer> users)
  {
    this.users = users;
  }

  @Override
  public void addBonusPoints(String userId, Integer additionalUserPoints)
  {
    Integer userPoints = users.get(userId);
    users.put(userId, userPoints == null ? additionalUserPoints : Integer.valueOf(userPoints + additionalUserPoints));
  }

  @Override
  public Integer getPoints(String userId)
  {
    Integer userPoints = users.get(userId);
    return userPoints == null ? Integer.valueOf(0) : userPoints;
  }
}
