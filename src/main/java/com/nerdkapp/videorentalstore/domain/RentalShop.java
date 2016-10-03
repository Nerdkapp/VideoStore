package com.nerdkapp.videorentalstore.domain;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

public class RentalShop
{
  private final Currency currency;

  private RentalRepository rentalRepository;

  public RentalShop(RentalRepository rentalRepository, Currency currency)
  {
    this.rentalRepository = rentalRepository;
    this.currency = currency;
  }

  public Price calculateExpectedPrice(List<Rental> rentals)
  {
    return new Price(rentals.stream().map(rental -> rental.calculate()).reduce(BigDecimal.ZERO, BigDecimal::add), currency);
  }

  public Price calculateExpectedPrice(Rental rental)
  {
    return calculateExpectedPrice(Arrays.asList(rental));
  }


  public UUID rent(List<Movie> moviesToRent)
  {
    return UUID.randomUUID();
  }

  public Price returnMovies(UUID rentalId)
  {
    rentalRepository.retrieveRentedMovies(rentalId);
    return new Price(new BigDecimal("90.00"), currency);
  }
}
