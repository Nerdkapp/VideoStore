package com.nerdkapp.videorentalstore.domain.movies;

import com.nerdkapp.videorentalstore.domain.movies.Movie;

import java.util.List;

public interface MoviesRepository
{
  List<Movie> findMovies(List<String> movies);
}
