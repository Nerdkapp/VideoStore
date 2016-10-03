package com.nerdkapp.videorentalstore.domain;

import java.util.List;
import java.util.UUID;

public interface RentalRepository
{
  List<Movie> retrieveRentedMovies(UUID rentalId);
}
