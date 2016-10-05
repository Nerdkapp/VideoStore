package com.nerdkapp.videorentalstore.infrastructure.rental;

import com.nerdkapp.videorentalstore.domain.*;
import com.nerdkapp.videorentalstore.domain.movies.Movie;
import com.nerdkapp.videorentalstore.domain.movies.RentedMovies;
import com.nerdkapp.videorentalstore.domain.rental.Rental;
import com.nerdkapp.videorentalstore.domain.rental.RentalRepository;
import com.nerdkapp.videorentalstore.domain.rental.RentalShop;
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
  private Clock clock;

  @Autowired
  public DefaultRentalShop(RentalRepository rentalRepository, Currency currency, Clock clock)
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

    BigDecimal amountToPay = new BigDecimal(0);

    for(Movie movie : rentedMovies.getMovies())
    {
      amountToPay = amountToPay.add(movie.getPricingModel().calculatePrice(daysOfRental));
    }

    return new Price(amountToPay, currency);
  }
}
