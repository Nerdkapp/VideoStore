package com.nerdkapp.videorentalstore.domain;

import com.nerdkapp.videorentalstore.domain.movies.Movie;
import com.nerdkapp.videorentalstore.domain.movies.MovieNotFoundException;
import com.nerdkapp.videorentalstore.domain.movies.RentedMovies;
import com.nerdkapp.videorentalstore.domain.movies.pricing.PremiumMoviePricing;
import com.nerdkapp.videorentalstore.domain.movies.pricing.RegularMoviePricing;
import com.nerdkapp.videorentalstore.domain.rental.Rental;
import com.nerdkapp.videorentalstore.domain.rental.RentalReceipt;
import com.nerdkapp.videorentalstore.domain.rental.RentalRepository;
import com.nerdkapp.videorentalstore.domain.rental.RentalShop;
import com.nerdkapp.videorentalstore.infrastructure.rental.DefaultRentalShop;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.Currency;
import java.util.UUID;

import static org.junit.Assert.*;

public class DefaultRentalShopTest
{
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  RentalRepository rentalRepository = context.mock(RentalRepository.class);
  Clock clock = context.mock(Clock.class);
  RentalShop rentalShop = new DefaultRentalShop(rentalRepository, Currency.getInstance("SEK"), clock);

  Currency SEK = Currency.getInstance("SEK");

  @Test
  public void calculate_price_for_a_premium_movie() throws Exception
  {
    Movie movie = new Movie("Matrix", new PremiumMoviePricing());
    int numberOfDays = 1;

    Price price = rentalShop.calculateExpectedPrice(new Rental(movie, numberOfDays));

    Price expectedPrice = new Price( new BigDecimal("40.00"), Currency.getInstance("SEK"));

    assertEquals(expectedPrice, price);
  }

  @Test
  public void calculate_price_for_a_regular_movie() throws Exception
  {
    Movie movie = new Movie("Matrix", new RegularMoviePricing());
    int numberOfDays = 5;

    Price price = rentalShop.calculateExpectedPrice(new Rental(movie, numberOfDays));

    Price expectedPrice = new Price( new BigDecimal("90.00"), Currency.getInstance("SEK"));

    assertEquals(expectedPrice, price);
  }

  @Test
  public void calculate_price_for_multiple_movies() throws Exception
  {
    Rental firstRental = new Rental(new Movie("Matrix", new PremiumMoviePricing()), 1);
    Rental secondRental = new Rental(new Movie("Spiderman 100", new RegularMoviePricing()), 5);

    Price price = rentalShop.calculateExpectedPrice(Arrays.asList(firstRental, secondRental));

    Price expectedPrice = new Price( new BigDecimal("130.00"), Currency.getInstance("SEK"));

    assertEquals(expectedPrice, price);
  }

  @Test
  public void rent_a_movie() throws Exception
  {
    String movie = "Spiderman";
    Movie movieFoundOnRepo = new Movie(movie, new RegularMoviePricing());

    UUID rentalId = UUID.randomUUID();
    Price expectedPrice = new Price(new BigDecimal("30.00"), Currency.getInstance("SEK"));

    context.checking(new Expectations(){{
      oneOf(rentalRepository).findMovie(movie);
      will(returnValue(movieFoundOnRepo));
      oneOf(rentalRepository).rentMovies(Arrays.asList(movieFoundOnRepo));
      will(returnValue(rentalId));
    }});

    RentalReceipt receipt = rentalShop.rent(Arrays.asList(movie), today(), tomorrow());
    assertEquals(rentalId, receipt.getRentalId());
    assertEquals(expectedPrice, receipt.getPrice());
  }

  private LocalDate tomorrow()
  {
    return LocalDate.now().plus(1L, ChronoUnit.DAYS);
  }

  private LocalDate today()
  {
    return LocalDate.now();
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
      oneOf(rentalRepository).rentMovies(Arrays.asList(movieFoundOnRepo));
      will(throwException(new MovieNotFoundException()));
    }});

    RentalReceipt receipt = rentalShop.rent(Arrays.asList(movie), today(), tomorrow());
    assertEquals(rentalId, receipt.getRentalId());
  }

  @Test
  public void return_a_movie() throws Exception
  {
    UUID rentalId = UUID.randomUUID();
    RentedMovies rentedMovies = new RentedMovies(
        rentalId,
        Arrays.asList(new Movie("Spiderman 100", new RegularMoviePricing())),
        LocalDate.now());

    context.checking(new Expectations(){{
      oneOf(rentalRepository).retrieveRentedMovies(rentalId);
        will(returnValue(rentedMovies));
      oneOf(clock).now();
        will(returnValue(LocalDate.now().plus(5, ChronoUnit.DAYS)));
    }});

    Price price = rentalShop.returnMovies(rentalId);

    assertEquals(new Price(new BigDecimal("90.00"), SEK), price);
  }

  @Test
  public void return_multiple_movies() throws Exception
  {
    UUID rentalId = UUID.randomUUID();
    RentedMovies rentedMovies = new RentedMovies(
        rentalId,
        Arrays.asList(new Movie("Spiderman 100", new RegularMoviePricing()), new Movie("Matrix", new PremiumMoviePricing())),
        LocalDate.now());

    context.checking(new Expectations(){{
        oneOf(rentalRepository).retrieveRentedMovies(rentalId);
      will(returnValue(rentedMovies));
        oneOf(clock).now();
      will(returnValue(LocalDate.now().plus(3, ChronoUnit.DAYS)));
    }});

    Price price = rentalShop.returnMovies(rentalId);

    assertEquals(new Price(new BigDecimal("150.00"), SEK), price);
  }
}