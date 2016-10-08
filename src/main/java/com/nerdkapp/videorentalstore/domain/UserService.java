package com.nerdkapp.videorentalstore.domain;

import com.nerdkapp.videorentalstore.domain.movies.Movie;

import java.util.List;

public interface UserService
{
  void addBonusPoints(String userId, List<Movie> movieFoundOnRepo);
}
