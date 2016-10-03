package com.nerdkapp.videorentalstore.domain;

import java.util.UUID;

public interface RentalRepository
{
  RentedMovies retrieveRentedMovies(UUID rentalId);
}
