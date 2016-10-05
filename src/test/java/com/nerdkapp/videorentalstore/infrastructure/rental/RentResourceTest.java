package com.nerdkapp.videorentalstore.infrastructure.rental;

import com.nerdkapp.videorentalstore.domain.Price;
import com.nerdkapp.videorentalstore.domain.rental.RentalReceipt;
import com.nerdkapp.videorentalstore.domain.rental.RentalShop;
import com.nerdkapp.videorentalstore.infrastructure.rental.json.*;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class RentResourceTest
{
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private RentalShop rentalShop = context.mock(RentalShop.class);
  private RentResource rentResource = new RentResource(rentalShop);

  @Test
  public void rent_a_movie() throws Exception
  {
    RentalRequest rentalRequest = aRentalRequest();
    List<String> moviesList = rentalRequest.getMovies().stream().
                              map(r -> r.getTitle()).
                              collect(Collectors.toList());

    UUID rentalId = UUID.randomUUID();
    Price price = new Price(new BigDecimal("10.00"), Currency.getInstance("SEK"));

    context.checking(new Expectations(){{
      oneOf(rentalShop).rent(
          moviesList,
          rentalRequest.getStartRentalDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
          rentalRequest.getEndRentalDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
      will(returnValue(new RentalReceipt(rentalId, price)));
    }});

    RentalResponse rentalResponse = rentResource.rent("lcoccia", rentalRequest);

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
      oneOf(rentalShop).returnMovies(rentalId, tomorrow());
      will(returnValue(price));
    }});

    ReturnMoviesResponse rentalResponse = rentResource.returnMovies("lcoccia", rentalId, new ReturnMoviesRequest(tomorrowAsDate()));
    assertEquals(new BigDecimal("100.00"), rentalResponse.getAmountToPay());
    assertEquals(Currency.getInstance("SEK"), rentalResponse.getCurrency());
  }

  private LocalDate tomorrow()
  {
    return LocalDate.now().plus(1L, ChronoUnit.DAYS);
  }

  private Date tomorrowAsDate(){
    Calendar c = Calendar.getInstance();
    c.setTime(new Date());
    c.add(Calendar.DATE, 1);
    return c.getTime();
  }

  private RentalRequest aRentalRequest() throws ParseException
  {
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    List<Movie> movies = Arrays.asList(new Movie("Matrix"));
    return new RentalRequest(movies,
        dateFormat.parse("2016/10/20"),
        dateFormat.parse("2016/10/22"));
  }
}