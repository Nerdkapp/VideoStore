package com.nerdkapp.videorentalstore.domain.rental;

import com.nerdkapp.videorentalstore.domain.movies.Movie;
import com.nerdkapp.videorentalstore.domain.movies.RentedMovies;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RentalRepository
{
  RentedMovies retrieveRentedMovies(UUID rentalId);
  Movie findMovie(String movie);
  UUID rentMovies(List<Movie> movies, LocalDate expectedReturnDate);
  void closeRental(UUID rentalId);
}
