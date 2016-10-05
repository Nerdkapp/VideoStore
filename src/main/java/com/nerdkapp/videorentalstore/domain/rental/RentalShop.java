package com.nerdkapp.videorentalstore.domain.rental;

import com.nerdkapp.videorentalstore.domain.Price;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RentalShop
{
  Price calculateExpectedPrice(List<Rental> rentals);

  Price calculateExpectedPrice(Rental rental);

  Price returnMovies(UUID rentalId, LocalDate localDate);

  RentalReceipt rent(List<String> movies, LocalDate startRentalDate, LocalDate endRentalData);
}
