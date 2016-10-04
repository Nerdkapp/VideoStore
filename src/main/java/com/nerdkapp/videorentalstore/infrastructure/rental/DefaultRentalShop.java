package com.nerdkapp.videorentalstore.infrastructure.rental;

import com.nerdkapp.videorentalstore.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class DefaultRentalShop implements RentalShop
{
  private final Currency currency;

  private RentalRepository rentalRepository;
  private RegularClock clock;

  @Autowired
  public DefaultRentalShop(RentalRepository rentalRepository, Currency currency, RegularClock clock)
  {
    this.rentalRepository = rentalRepository;
    this.currency = currency;
    this.clock = clock;
  }

  @Override
  public Price calculateExpectedPrice(List<Rental> rentals)
  {
    return new Price(rentals.stream().map(rental -> rental.calculate()).reduce(BigDecimal.ZERO, BigDecimal::add), currency);
  }

  @Override
  public Price calculateExpectedPrice(Rental rental)
  {
    return calculateExpectedPrice(Arrays.asList(rental));
  }


  @Override
  public UUID rent(List<String> moviesToRent)
  {
    List<Movie> movies = moviesToRent.stream().map(title -> rentalRepository.findMovie(title)).collect(Collectors.toList());
    return rentalRepository.rentMovies(movies);
  }

  @Override
  public Price returnMovies(UUID rentalId)
  {
    RentedMovies rentedMovies = rentalRepository.retrieveRentedMovies(rentalId);
    int daysOfRental = (int) ChronoUnit.DAYS.between(rentedMovies.getRentalDate(), clock.now());

    return new Price(rentedMovies.getMovies().stream().
        map(movie -> movie.getPricingModel().calculatePrice(daysOfRental)).
        reduce(BigDecimal.ZERO, BigDecimal::add), currency);
  }
}
