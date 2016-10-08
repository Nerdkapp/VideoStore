package com.nerdkapp.videorentalstore.domain;

import com.nerdkapp.videorentalstore.domain.movies.Movie;
import com.nerdkapp.videorentalstore.domain.movies.pricing.PremiumMoviePricing;
import com.nerdkapp.videorentalstore.domain.movies.pricing.RegularMoviePricing;
import com.nerdkapp.videorentalstore.domain.rental.RentalRepository;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;

public class DefaultRentalServiceTest
{
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  RentalRepository rentalRepository = context.mock(RentalRepository.class);
  UserRepository userRepository = context.mock(UserRepository.class);
  RentalService rentalService = new DefaultRentalService(Currency.getInstance("SEK"), rentalRepository, userRepository);

  @Test
  public void pricing_multiple_movies() throws Exception
  {
    List<Movie> movies = Arrays.asList(
        new Movie("Matrix", new PremiumMoviePricing()),
        new Movie("Spiderman 100", new RegularMoviePricing()));

    Price price = rentalService.calculateRentalPrice(movies, 1);

    Price expectedPrice = new Price( new BigDecimal("70.00"), Currency.getInstance("SEK"));
    assertEquals(expectedPrice, price);
  }

  @Test
  public void pricing_regular_movie() throws Exception
  {
    List<Movie> movies = Arrays.asList(new Movie("Spiderman", new RegularMoviePricing()));

    Price price = rentalService.calculateRentalPrice(movies, 1);

    Price expectedPrice = new Price( new BigDecimal("30.00"), Currency.getInstance("SEK"));
    assertEquals(expectedPrice, price);
  }

  private LocalDate tomorrow()
  {
    return LocalDate.now().plus(1L, ChronoUnit.DAYS);
  }

  private LocalDate today()
  {
    return LocalDate.now();
  }
}