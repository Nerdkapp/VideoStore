package com.nerdkapp.videorentalstore.domain;

public interface UserRepository
{
  void addBonusPoints(String userId, Integer integer);

  Integer getPoints(String userId);
}
