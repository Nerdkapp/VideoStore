package com.nerdkapp.videorentalstore.domain;

import com.nerdkapp.videorentalstore.domain.movies.Movie;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RentalService
{
  Price calculateRentalPrice(List<Movie> moviesFoundOnRepo, int daysOfRental);

  Integer calculateBonusPoints(List<Movie> movies);

  UUID rent(String user, List<Movie> moviesToRent, LocalDate endRentalDate);

  Price closeRental(UUID rentalId, LocalDate returnDate);
}
