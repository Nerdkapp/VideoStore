package com.nerdkapp.videorentalstore.domain;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

public class RentalShop
{
  private final Currency currency;

  public RentalShop(Currency currency)
  {
    this.currency = currency;
  }

  public Price rent(List<Rental> rentals)
  {
    return new Price(rentals.stream().map(rental -> rental.calculate()).reduce(BigDecimal.ZERO, BigDecimal::add), currency);
  }

  public Price rent(Rental rental)
  {
    return rent(Arrays.asList(rental));
  }
}
