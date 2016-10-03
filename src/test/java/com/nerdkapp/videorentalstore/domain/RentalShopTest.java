package com.nerdkapp.videorentalstore.domain;

import com.nerdkapp.videorentalstore.domain.pricing.PremiumMoviePricing;
import com.nerdkapp.videorentalstore.domain.pricing.RegularMoviePricing;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class RentalShopTest
{
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  RentalRepository rentalRepository = context.mock(RentalRepository.class);
  RentalShop rentalShop = new RentalShop(rentalRepository, Currency.getInstance("SEK"));

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
    UUID rentalId = rentalShop.rent(Arrays.asList(new Movie("Spiderman 100", new RegularMoviePricing())));
    assertNotNull(rentalId);
  }

  @Test
  public void return_a_movie() throws Exception
  {
    List<Movie> moviesToRent = Arrays.asList(new Movie("Spiderman 100", new RegularMoviePricing()));
    UUID rentalId = rentalShop.rent(moviesToRent);

    context.checking(new Expectations(){{
      oneOf(rentalRepository).retrieveRentedMovies(rentalId);
        will(returnValue(moviesToRent));
    }});

    Price price = rentalShop.returnMovies(rentalId);

    assertEquals(new Price(new BigDecimal("90.00"), SEK), price);
  }
}