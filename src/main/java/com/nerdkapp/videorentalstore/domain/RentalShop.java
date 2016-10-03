package com.nerdkapp.videorentalstore.domain;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

public class RentalShop
{
  private final Currency currency;

  public RentalShop(Currency currency)
  {
    this.currency = currency;
  }

  public Price calculateExpectedPrice(List<Rental> rentals)
  {
    return new Price(rentals.stream().map(rental -> rental.calculate()).reduce(BigDecimal.ZERO, BigDecimal::add), currency);
  }

  public Price calculateExpectedPrice(Rental rental)
  {
    return calculateExpectedPrice(Arrays.asList(rental));
  }


  public UUID rent(Movie movie)
  {
    return UUID.randomUUID();
  }
}
