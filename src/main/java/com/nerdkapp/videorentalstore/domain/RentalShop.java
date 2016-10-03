package com.nerdkapp.videorentalstore.domain;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class RentalShop
{
  private final Currency currency;

  private RentalRepository rentalRepository;
  private RegularClock clock;

  public RentalShop(RentalRepository rentalRepository, Currency currency, RegularClock clock)
  {
    this.rentalRepository = rentalRepository;
    this.currency = currency;
    this.clock = clock;
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
    RentedMovies rentedMovies = rentalRepository.retrieveRentedMovies(rentalId);
    int daysOfRental = (int) ChronoUnit.DAYS.between(rentedMovies.getRentalDate(), clock.now());

    return new Price(rentedMovies.getMovies().stream().
        map(movie -> movie.getPricingModel().calculatePrice(daysOfRental)).
        reduce(BigDecimal.ZERO, BigDecimal::add), currency);
  }
}
