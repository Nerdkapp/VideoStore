package com.nerdkapp.videorentalstore.infrastructure.rental;

import com.nerdkapp.videorentalstore.domain.Price;
import com.nerdkapp.videorentalstore.domain.rental.RentalReceipt;
import com.nerdkapp.videorentalstore.domain.rental.RentalShop;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    RentResource.RentalRequest rentalRequest = aRentalRequest();
    List<String> moviesList = rentalRequest.getMovies().stream().
                              map(r -> r.getTitle()).
                              collect(Collectors.toList());

    UUID rentalId = UUID.randomUUID();
    Price price = new Price(new BigDecimal("10.00"), Currency.getInstance("SEK"));

    context.checking(new Expectations(){{
      oneOf(rentalShop).rent(moviesList, rentalRequest.getStartRentalDate(), rentalRequest.getEndRentalData());
      will(returnValue(new RentalReceipt(rentalId, price)));
    }});

    RentResource.RentalResponse rentalResponse = rentResource.rent("lcoccia", rentalRequest);

    assertEquals(rentalId, rentalResponse.getRentalId());
    assertEquals(price.getAmount(), rentalResponse.getAmountToPay());
    assertEquals(price.getCurrency(), rentalResponse.getCurrency());
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
    return new RentResource.RentalRequest(movies, LocalDate.of(2016,10,20), LocalDate.of(2016,10,22));
  }
}