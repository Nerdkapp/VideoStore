package com.nerdkapp.videorentalstore.domain.rental;

import com.nerdkapp.videorentalstore.domain.Price;
import com.nerdkapp.videorentalstore.infrastructure.rental.RentResource;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RentalShop
{
  Price calculateExpectedPrice(List<Rental> rentals);

  Price calculateExpectedPrice(Rental rental);

  Price returnMovies(UUID rentalId);

  RentalReceipt rent(List<String> movies, LocalDate startRentalDate, LocalDate endRentalData);
}
