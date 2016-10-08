package com.nerdkapp.videorentalstore.domain.movies;

import java.util.List;

public interface MoviesRepository
{
  List<Movie> findMovies(List<String> movies);
}
