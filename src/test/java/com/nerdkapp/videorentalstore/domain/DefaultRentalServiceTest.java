package com.nerdkapp.videorentalstore.domain;

import com.nerdkapp.videorentalstore.domain.movies.Movie;
import com.nerdkapp.videorentalstore.domain.movies.pricing.OldMovie;
import com.nerdkapp.videorentalstore.domain.movies.pricing.PremiumMovie;
import com.nerdkapp.videorentalstore.domain.movies.pricing.RegularMovie;
import com.nerdkapp.videorentalstore.domain.rental.RentedMovies;
import com.nerdkapp.videorentalstore.domain.user.UserRepository;
import com.nerdkapp.videorentalstore.infrastructure.movie.MovieTypeFactory;
import com.nerdkapp.videorentalstore.infrastructure.rental.DefaultRentalService;
import com.nerdkapp.videorentalstore.domain.rental.RentalRepository;
import com.nerdkapp.videorentalstore.domain.rental.RentalService;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class DefaultRentalServiceTest
{
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  RentalRepository rentalRepository = context.mock(RentalRepository.class);
  UserRepository userRepository = context.mock(UserRepository.class);
  RentalService rentalService = new DefaultRentalService(Currency.getInstance("SEK"), rentalRepository, userRepository);

  @Test
  public void rent_a_movie() throws Exception
  {
    UUID rentalId = UUID.randomUUID();

    String user = "lcoccia";
    List<Movie> movies = Arrays.asList(
        new Movie("Matrix", MovieTypeFactory.oldMovie()),
        new Movie("Spiderman 100", MovieTypeFactory.regularMovie()));

    context.checking(new Expectations(){{
      oneOf(rentalRepository).rentMovies(movies, tomorrow());
        will(returnValue(rentalId));
      oneOf(userRepository).addBonusPoints(user, 2);
    }});

    rentalService.rent(user, movies, tomorrow());
  }

  @Test
  public void close_a_rental() throws Exception
  {
    UUID rentalId = UUID.randomUUID();

    RentedMovies rentedMovies = new RentedMovies(
        rentalId,
        Arrays.asList(new Movie("Spiderman 100", MovieTypeFactory.regularMovie()), new Movie("Matrix", MovieTypeFactory.premiumMovie())),
        today(), tomorrow());

    context.checking(new Expectations(){{
      oneOf(rentalRepository).findRental(rentalId);
      will(returnValue(rentedMovies));
      oneOf(rentalRepository).closeRental(rentalId);
    }});

    rentalService.closeRental(rentalId, tomorrow());

  }


  private LocalDate today()
  {
    return LocalDate.now();
  }
  private LocalDate tomorrow()
  {
    return LocalDate.now().plus(1L, ChronoUnit.DAYS);
  }

  @Test
  public void pricing_multiple_movies() throws Exception
  {
    List<Movie> movies = Arrays.asList(
        new Movie("Matrix", new PremiumMovie()),
        new Movie("Spiderman 100", new RegularMovie()));

    Price price = rentalService.calculateRentalPrice(movies, 1);

    Price expectedPrice = new Price( new BigDecimal("70.00"), Currency.getInstance("SEK"));
    assertEquals(expectedPrice, price);
  }

  @Test
  public void pricing_regular_movie() throws Exception
  {
    List<Movie> movies = Arrays.asList(new Movie("Spiderman", new RegularMovie()));

    assertEquals(new Price( new BigDecimal("90.00"), Currency.getInstance("SEK")), rentalService.calculateRentalPrice(movies, 5));
    assertEquals(new Price( new BigDecimal("30.00"), Currency.getInstance("SEK")), rentalService.calculateRentalPrice(movies, 2));
  }

  @Test
  public void pricing_premium_movie() throws Exception
  {
    List<Movie> movies = Arrays.asList(new Movie("Matrix 11", new PremiumMovie()));

    Price price = rentalService.calculateRentalPrice(movies, 1);

    Price expectedPrice = new Price( new BigDecimal("40.00"), Currency.getInstance("SEK"));
    assertEquals(expectedPrice, price);
  }

  @Test
  public void pricing_old_movie() throws Exception
  {
    List<Movie> movies = Arrays.asList(new Movie("Spider man", new OldMovie()));

    Price price = rentalService.calculateRentalPrice(movies, 7);

    Price expectedPrice = new Price( new BigDecimal("90.00"), Currency.getInstance("SEK"));
    assertEquals(expectedPrice, price);
  }

  @Test
  public void bonus_points_for_regular_or_old_movies() throws Exception
  {
    List<Movie> oldMovies = Arrays.asList(new Movie("Spider man", new OldMovie()));
    List<Movie> regularMovies = Arrays.asList(new Movie("Spider man 2", new RegularMovie()));

    assertEquals(new Integer(1), rentalService.calculateBonusPoints(oldMovies));
    assertEquals(new Integer(1), rentalService.calculateBonusPoints(regularMovies));
  }

  @Test
  public void bonus_points_for_premium_movies() throws Exception
  {
    List<Movie> premiumMovies = Arrays.asList(new Movie("Spider man 1000", new PremiumMovie()));

    assertEquals(new Integer(2), rentalService.calculateBonusPoints(premiumMovies));
  }
}