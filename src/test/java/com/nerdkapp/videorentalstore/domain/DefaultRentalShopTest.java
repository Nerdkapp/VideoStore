package com.nerdkapp.videorentalstore.domain;

import com.nerdkapp.videorentalstore.domain.movies.Movie;
import com.nerdkapp.videorentalstore.domain.movies.MovieNotFoundException;
import com.nerdkapp.videorentalstore.domain.movies.MoviesRepository;
import com.nerdkapp.videorentalstore.domain.movies.pricing.PremiumMovie;
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

  RentalService rentalService = context.mock(RentalService.class);
  MoviesRepository moviesRepository = context.mock(MoviesRepository.class);

  RentalShop rentalShop = new DefaultRentalShop(moviesRepository, rentalService, Currency.getInstance("SEK"));

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
  public void rent_a_movie() throws Exception
  {
    List<String> movies = Arrays.asList("Spiderman");
    List<Movie> moviesFoundOnRepo = Arrays.asList(new Movie("Spiderman", new PremiumMovie()));
    String userId = "an_user";

    UUID rentalId = UUID.randomUUID();
    Price expectedPrice = new Price(new BigDecimal("40.00"), Currency.getInstance("SEK"));

    context.checking(new Expectations(){{
      oneOf(moviesRepository).findMovies(movies);
        will(returnValue(moviesFoundOnRepo));
      oneOf(rentalService).calculateRentalPrice(moviesFoundOnRepo, 1);
        will(returnValue(expectedPrice));
      oneOf(rentalService).rent(userId, moviesFoundOnRepo, tomorrow());
        will(returnValue(rentalId));
    }});

    RentalReceipt receipt = rentalShop.rent(userId, movies, today(), tomorrow());
    assertEquals(rentalId, receipt.getRentalId());
    assertEquals(expectedPrice, receipt.getPrice());
  }

  @Test(expected = MovieNotFoundException.class)
  public void throw_expection_when_movie_is_not_found() throws Exception
  {
    String movie = "Movie not existent";

    UUID rentalId = UUID.randomUUID();

    context.checking(new Expectations(){{
      oneOf(moviesRepository).findMovies(Arrays.asList(movie));
        will(throwException(new MovieNotFoundException()));
    }});

    RentalReceipt receipt = rentalShop.rent("an_user", Arrays.asList(movie), today(), tomorrow());
    assertEquals(rentalId, receipt.getRentalId());
  }

  @Test
  public void return_a_movie() throws Exception
  {
    UUID rentalId = UUID.randomUUID();

    context.checking(new Expectations(){{
      oneOf(rentalService).closeRental(rentalId, tomorrow());
      will(returnValue(new Price(new BigDecimal("0"), SEK)));
    }});

    Price price = rentalShop.returnMovies(rentalId, tomorrow());

    assertEquals(new Price(new BigDecimal("0"), SEK), price);
  }

  @Test(expected = RentalNotFoundException.class)
  public void throw_exception_when_rental_is_not_found() throws Exception
  {
    UUID rentalId = UUID.randomUUID();
    context.checking(new Expectations(){{
      oneOf(rentalService).closeRental(rentalId, tomorrow());
      will(throwException(new RentalNotFoundException()));
    }});

    Price price = rentalShop.returnMovies(rentalId, tomorrow());

    assertEquals(new Price(new BigDecimal("0"), SEK), price);
  }
}