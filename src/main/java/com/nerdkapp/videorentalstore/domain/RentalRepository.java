package com.nerdkapp.videorentalstore.domain;

import java.util.List;
import java.util.UUID;

public interface RentalRepository
{
  RentedMovies retrieveRentedMovies(UUID rentalId);
  Movie findMovie(String movie);
  UUID rentMovies(List<Movie> movies);
}
