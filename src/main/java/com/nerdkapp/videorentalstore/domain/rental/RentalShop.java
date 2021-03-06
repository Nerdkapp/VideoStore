package com.nerdkapp.videorentalstore.domain.rental;

import com.nerdkapp.videorentalstore.domain.Price;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RentalShop
{
  Price returnMovies(UUID rentalId, LocalDate localDate);
  RentalReceipt rent(String user, List<String> movies, LocalDate startRentalDate, LocalDate endRentalData);
}
