package com.nerdkapp.videorentalstore.domain;

import com.nerdkapp.videorentalstore.domain.movies.Movie;

import java.util.List;
import java.util.UUID;

public interface RentalRepository
{
  RentedMovies retrieveRentedMovies(UUID rentalId);
  Movie findMovie(String movie);
  UUID rentMovies(List<Movie> movies);
}
