package com.nerdkapp.videorentalstore.domain.rental;

import com.nerdkapp.videorentalstore.domain.movies.Movie;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RentalRepository
{
  RentedMovies findRental(UUID rentalId);
  UUID rentMovies(List<Movie> movies, LocalDate expectedReturnDate);
  void closeRental(UUID rentalId);
}
