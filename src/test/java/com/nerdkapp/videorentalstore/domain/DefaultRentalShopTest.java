package com.nerdkapp.videorentalstore.domain;

import com.nerdkapp.videorentalstore.domain.movies.Movie;
import com.nerdkapp.videorentalstore.domain.movies.MovieNotFoundException;
import com.nerdkapp.videorentalstore.domain.movies.RentedMovies;
import com.nerdkapp.videorentalstore.domain.movies.pricing.PremiumMoviePricing;
import com.nerdkapp.videorentalstore.domain.movies.pricing.RegularMoviePricing;
import com.nerdkapp.videorentalstore.domain.rental.*;
import com.nerdkapp.videorentalstore.infrastructure.rental.DefaultRentalShop;
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

import static org.junit.Assert.assertEquals;

public class DefaultRentalShopTest
{
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  RentalRepository rentalRepository = context.mock(RentalRepository.class);
  UserService userService = context.mock(UserService.class);

  RentalShop rentalShop = new DefaultRentalShop(rentalRepository, userService, Currency.getInstance("SEK"));

  Currency SEK = Currency.getInstance("SEK");

  private LocalDate tomorrow()
  {
    return LocalDate.now().plus(1L, ChronoUnit.DAYS);
  }

  private LocalDate today()
  {
    return LocalDate.now();
  }

  @Test
  public void rent_a_premium_movie() throws Exception
  {
    String movie = "Spiderman";
    Movie movieFoundOnRepo = new Movie(movie, new PremiumMoviePricing());
    String userId = "an_user";

    UUID rentalId = UUID.randomUUID();
    Price expectedPrice = new Price(new BigDecimal("40.00"), Currency.getInstance("SEK"));

    context.checking(new Expectations(){{
      oneOf(rentalRepository).findMovie(movie);
        will(returnValue(movieFoundOnRepo));
      oneOf(rentalRepository).rentMovies(Arrays.asList(movieFoundOnRepo), tomorrow());
        will(returnValue(rentalId));
      oneOf(userService).addBonusPoints(userId, Arrays.asList(movieFoundOnRepo));
    }});

    RentalReceipt receipt = rentalShop.rent(userId, Arrays.asList(movie), today(), tomorrow());
    assertEquals(rentalId, receipt.getRentalId());
    assertEquals(expectedPrice, receipt.getPrice());
  }

  @Test
  public void rent_a_regular_movie() throws Exception
  {
    String movie = "Spiderman";
    Movie movieFoundOnRepo = new Movie(movie, new RegularMoviePricing());
    String userId = "an_user";

    UUID rentalId = UUID.randomUUID();
    Price expectedPrice = new Price(new BigDecimal("30.00"), Currency.getInstance("SEK"));


    context.checking(new Expectations(){{
      oneOf(rentalRepository).findMovie(movie);
        will(returnValue(movieFoundOnRepo));
      oneOf(rentalRepository).rentMovies(Arrays.asList(movieFoundOnRepo), tomorrow());
        will(returnValue(rentalId));
      oneOf(userService).addBonusPoints(userId, Arrays.asList(movieFoundOnRepo));
    }});

    RentalReceipt receipt = rentalShop.rent(userId, Arrays.asList(movie), today(), tomorrow());
    assertEquals(rentalId, receipt.getRentalId());
    assertEquals(expectedPrice, receipt.getPrice());
  }

  @Test
  public void calculate_price_for_multiple_movies() throws Exception
  {
    List<String> movies = Arrays.asList("Matrix", "Spiderman 100");
    Movie firstMovie = new Movie("Matrix", new PremiumMoviePricing());
    Movie secondMovie = new Movie("Spiderman 100", new RegularMoviePricing());
    String userId = "an_user";

    UUID rentalId = UUID.randomUUID();

    context.checking(new Expectations(){{
      oneOf(rentalRepository).findMovie("Matrix");
      will(returnValue(firstMovie));
      oneOf(rentalRepository).findMovie("Spiderman 100");
      will(returnValue(secondMovie));
      oneOf(rentalRepository).rentMovies(Arrays.asList(firstMovie, secondMovie), tomorrow());
      will(returnValue(rentalId));
      oneOf(userService).addBonusPoints(userId, Arrays.asList(firstMovie, secondMovie));

    }});

    RentalReceipt receipt = rentalShop.rent(userId, movies, today(), tomorrow());

    Price expectedPrice = new Price( new BigDecimal("70.00"), Currency.getInstance("SEK"));
    assertEquals(expectedPrice, receipt.getPrice());
    assertEquals(rentalId, receipt.getRentalId());
  }

  @Test(expected = MovieNotFoundException.class)
  public void throw_expection_when_movie_is_not_found() throws Exception
  {
    String movie = "Movie not existent";
    Movie movieFoundOnRepo = new Movie(movie, new RegularMoviePricing());

    UUID rentalId = UUID.randomUUID();

    context.checking(new Expectations(){{
      oneOf(rentalRepository).findMovie(movie);
      will(returnValue(movieFoundOnRepo));
      oneOf(rentalRepository).rentMovies(Arrays.asList(movieFoundOnRepo), tomorrow());
      will(throwException(new MovieNotFoundException()));
    }});

    RentalReceipt receipt = rentalShop.rent("an_user", Arrays.asList(movie), today(), tomorrow());
    assertEquals(rentalId, receipt.getRentalId());
  }

  @Test
  public void return_a_movie() throws Exception
  {
    UUID rentalId = UUID.randomUUID();
    RentedMovies rentedMovies = new RentedMovies(
        rentalId,
        Arrays.asList(new Movie("Spiderman 100", new RegularMoviePricing())),
        today(), tomorrow());

    context.checking(new Expectations(){{
      oneOf(rentalRepository).retrieveRentedMovies(rentalId);
        will(returnValue(rentedMovies));
      oneOf(rentalRepository).closeRental(rentalId);
    }});

    Price price = rentalShop.returnMovies(rentalId, tomorrow());

    assertEquals(new Price(new BigDecimal("0"), SEK), price);
  }

  @Test
  public void return_multiple_movies() throws Exception
  {
    UUID rentalId = UUID.randomUUID();
    RentedMovies rentedMovies = new RentedMovies(
        rentalId,
        Arrays.asList(new Movie("Spiderman 100", new RegularMoviePricing()), new Movie("Matrix", new PremiumMoviePricing())),
        today(), tomorrow());

    context.checking(new Expectations(){{
        oneOf(rentalRepository).retrieveRentedMovies(rentalId);
      will(returnValue(rentedMovies));
      oneOf(rentalRepository).closeRental(rentalId);
    }});

    Price price = rentalShop.returnMovies(rentalId, tomorrow());

    assertEquals(new Price(new BigDecimal("0"), SEK), price);
  }

  @Test(expected = RentalNotFoundException.class)
  public void throw_exception_when_rental_is_not_found() throws Exception
  {
    UUID rentalId = UUID.randomUUID();
    context.checking(new Expectations(){{
      oneOf(rentalRepository).retrieveRentedMovies(rentalId);
      will(throwException(new RentalNotFoundException()));
    }});

    Price price = rentalShop.returnMovies(rentalId, tomorrow());

    assertEquals(new Price(new BigDecimal("0"), SEK), price);
  }
}