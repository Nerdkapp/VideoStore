package com.nerdkapp.videorentalstore.domain;

import java.util.List;
import java.util.UUID;

public interface RentalShop
{
  Price calculateExpectedPrice(List<Rental> rentals);

  Price calculateExpectedPrice(Rental rental);

  UUID rent(List<String> moviesToRent);

  Price returnMovies(UUID rentalId);
}
