package com.nerdkapp.videorentalstore.domain;

import java.math.BigDecimal;
import java.util.Currency;

public class RentalShop
{
  private final Currency currency;

  public RentalShop(Currency currency)
  {
    this.currency = currency;
  }

  public Price rent(Movie movie, Integer numberOfDays)
  {
    return new Price(movie.calculatePrice(numberOfDays), currency);
  }
}
