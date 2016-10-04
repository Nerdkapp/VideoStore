package com.nerdkapp.videorentalstore.infrastructure.rental;

import com.nerdkapp.videorentalstore.domain.Movie;
import com.nerdkapp.videorentalstore.domain.Price;
import com.nerdkapp.videorentalstore.domain.RentalShop;
import com.nerdkapp.videorentalstore.domain.pricing.PremiumMoviePricing;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class RentResourceTest
{
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private RentalShop rentalShop = context.mock(RentalShop.class);
  private RentResource rentResource = new RentResource(rentalShop);

  @Test
  public void rent_a_movie() throws Exception
  {
    List<Movie> moviesToRent = Arrays.asList(new Movie("Matrix", new PremiumMoviePricing()));
    UUID rentalId = UUID.randomUUID();

    context.checking(new Expectations(){{
      oneOf(rentalShop).rent(moviesToRent);
      will(returnValue(rentalId));
    }});

    RentResource.RentalResponse rentalResponse = rentResource.rent("lcoccia", aRentalRequest());

    assertEquals(rentalId, rentalResponse.getRentalId());
  }

  @Test
  public void return_a_movie() throws Exception
  {
    UUID rentalId = UUID.randomUUID();
    Price price = new Price(new BigDecimal("100.00"), Currency.getInstance("SEK"));

    context.checking(new Expectations(){{
      oneOf(rentalShop).returnMovies(rentalId);
      will(returnValue(price));
    }});

    RentResource.ReturnMoviesResponse rentalResponse = rentResource.returnMovies("lcoccia", rentalId);
    assertEquals(new BigDecimal("100.00"), rentalResponse.getAmountToPay());
    assertEquals(Currency.getInstance("SEK"), rentalResponse.getCurrency());
  }


  private RentResource.RentalRequest aRentalRequest()
  {
    List<RentResource.MovieRequest> movies = Arrays.asList(new RentResource.MovieRequest("Matrix"));
    return new RentResource.RentalRequest(movies);
  }
}