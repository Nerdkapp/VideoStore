package com.nerdkapp.videorentalstore.domain;

import com.nerdkapp.videorentalstore.domain.movies.Movie;
import com.nerdkapp.videorentalstore.domain.movies.RentedMovies;
import com.nerdkapp.videorentalstore.domain.rental.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

@Component
public class DefaultRentalService implements RentalService
{
  private final Currency currency;
  private final RentalRepository rentalRepository;
  private final UserRepository userRepository;

  private final Price NO_PRICE_TO_PAY;

  @Autowired
  public DefaultRentalService(Currency currency, RentalRepository rentalRepository, UserRepository userRepository)
  {
    this.currency = currency;
    this.rentalRepository = rentalRepository;
    this.userRepository = userRepository;

    NO_PRICE_TO_PAY = new Price(BigDecimal.ZERO, currency);
  }

  @Override
  public Price calculateRentalPrice(List<Movie> moviesFoundOnRepo, int daysOfRental)
  {
    return new Price(moviesFoundOnRepo.stream().map(movie -> movie.getPricingModel().calculatePrice(daysOfRental)).reduce(BigDecimal.ZERO, BigDecimal::add), currency);
  }

  @Override
  public Integer calculateBonusPoints(List<Movie> movies)
  {
    return movies.stream().map(movie -> movie.getPricingModel().getBonusPointsForRental()).reduce(0, Integer::sum);
  }

  @Override
  public UUID rent(String user, List<Movie> moviesToRent, LocalDate endRentalDate)
  {
    UUID rentalId = rentalRepository.rentMovies(moviesToRent, endRentalDate);

    userRepository.addBonusPoints(user, calculateBonusPoints(moviesToRent));
    return rentalId;
  }

  @Override
  public Price closeRental(UUID rentalId, LocalDate returnDate)
  {
    rentalRepository.closeRental(rentalId);

    RentedMovies rentedMovies = rentalRepository.retrieveRentedMovies(rentalId);

    int additionalDaysOfRental = (int) ChronoUnit.DAYS.between(rentedMovies.getExpectedReturnDate(), returnDate);
    if(additionalDaysOfRental > 0)
      return calculateRentalPrice(rentedMovies.getMovies(), additionalDaysOfRental);

    return NO_PRICE_TO_PAY;
  }
}
