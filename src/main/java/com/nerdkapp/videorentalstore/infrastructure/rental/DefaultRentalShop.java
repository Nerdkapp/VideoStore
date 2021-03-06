package com.nerdkapp.videorentalstore.infrastructure.rental;

import com.nerdkapp.videorentalstore.domain.*;
import com.nerdkapp.videorentalstore.domain.movies.Movie;
import com.nerdkapp.videorentalstore.domain.movies.MoviesRepository;
import com.nerdkapp.videorentalstore.domain.rental.RentalReceipt;
import com.nerdkapp.videorentalstore.domain.rental.RentalRepository;
import com.nerdkapp.videorentalstore.domain.rental.RentalService;
import com.nerdkapp.videorentalstore.domain.rental.RentalShop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

@Component
public class DefaultRentalShop implements RentalShop
{
  private final RentalService rentalService;
  private final MoviesRepository moviesRepository;

  @Autowired
  public DefaultRentalShop(MoviesRepository moviesRepository, RentalService rentalService, Currency currency)
  {
    this.moviesRepository = moviesRepository;
    this.rentalService = rentalService;
  }

  @Override
  public RentalReceipt rent(String user, List<String> moviesToRent, LocalDate startRentalDate, LocalDate endRentalDate)
  {
    List<Movie> movies = moviesRepository.findMovies(moviesToRent);

    int daysOfRental = (int) ChronoUnit.DAYS.between(startRentalDate, endRentalDate);
    Price price = rentalService.calculateRentalPrice(movies, daysOfRental);
    UUID rentalId = rentalService.rent(user, movies, endRentalDate);

    return new RentalReceipt(rentalId, price);
  }

  @Override
  public Price returnMovies(UUID rentalId, LocalDate returnDate)
  {
    return rentalService.closeRental(rentalId, returnDate);
  }
}
