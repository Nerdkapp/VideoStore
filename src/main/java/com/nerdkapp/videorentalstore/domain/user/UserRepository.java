package com.nerdkapp.videorentalstore.domain.user;

public interface UserRepository
{
  void addBonusPoints(String userId, Integer integer);

  Integer getPoints(String userId);
}
