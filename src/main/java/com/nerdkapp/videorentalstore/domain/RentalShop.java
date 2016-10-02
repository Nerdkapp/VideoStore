package com.nerdkapp.videorentalstore.domain;

import java.math.BigDecimal;
import java.util.Currency;

public class RentalShop
{
  public Price rent(RecentMovie recentMovie, Integer numberOfDays)
  {
    return new Price(new BigDecimal("40.00"), Currency.getInstance("SEK"));
  }
}
