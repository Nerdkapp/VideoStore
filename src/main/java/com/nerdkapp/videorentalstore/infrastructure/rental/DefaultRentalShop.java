package com.nerdkapp.videorentalstore.infrastructure.rental;

import com.nerdkapp.videorentalstore.domain.*;
import com.nerdkapp.videorentalstore.domain.movies.Movie;
import com.nerdkapp.videorentalstore.domain.movies.RentedMovies;
import com.nerdkapp.videorentalstore.domain.rental.Rental;
import com.nerdkapp.videorentalstore.domain.rental.RentalReceipt;
import com.nerdkapp.videorentalstore.domain.rental.RentalRepository;
import com.nerdkapp.videorentalstore.domain.rental.RentalShop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
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

  @Autowired
  public DefaultRentalShop(RentalRepository rentalRepository, Currency currency)
  {
    this.rentalRepository = rentalRepository;
    this.currency = currency;
  }

  private Price calculateExpectedPrice(List<Movie> rentals, LocalDate startDate, LocalDate endDate)
  {
    int daysOfRental = (int) ChronoUnit.DAYS.between(startDate, endDate);
    return new Price(rentals.stream().map(rental -> rental.getPricingModel().calculatePrice(daysOfRental)).reduce(BigDecimal.ZERO, BigDecimal::add), currency);
  }

  @Override
  public RentalReceipt rent(List<String> moviesToRent, LocalDate startRentalDate, LocalDate endRentalDate)
  {
    List<Movie> movies = moviesToRent.stream().map(title -> rentalRepository.findMovie(title)).collect(Collectors.toList());
    UUID rentalId = rentalRepository.rentMovies(movies, endRentalDate);
    Price price = calculateExpectedPrice(movies, startRentalDate, endRentalDate);

    return new RentalReceipt(rentalId, price);
  }

  @Override
  public Price returnMovies(UUID rentalId, LocalDate localDate)
  {
    BigDecimal amountToPay = new BigDecimal(0);

    RentedMovies rentedMovies = rentalRepository.retrieveRentedMovies(rentalId);
    int additionalDaysOfRental = (int) ChronoUnit.DAYS.between(rentedMovies.getExpectedReturnDate(), localDate);

    if(additionalDaysOfRental > 0)
      for(Movie movie : rentedMovies.getMovies())
        amountToPay = amountToPay.add(movie.getPricingModel().calculatePrice(additionalDaysOfRental));

    return new Price(amountToPay, currency);
  }
}
