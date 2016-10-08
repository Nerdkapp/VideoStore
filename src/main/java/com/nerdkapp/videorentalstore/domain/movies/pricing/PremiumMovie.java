package com.nerdkapp.videorentalstore.domain.movies.pricing;

import java.math.BigDecimal;

public class PremiumMovie implements MovieType
{
  BigDecimal premiumPrice = new BigDecimal("40.00");

  @Override
  public BigDecimal calculatePrice(Integer daysOfRental)
  {
    if(daysOfRental > 1)
      return premiumPrice.multiply(new BigDecimal(daysOfRental));
    else
      return premiumPrice;
  }

  @Override
  public Integer getBonusPointsForRental()
  {
    return 2;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }

    PremiumMovie that = (PremiumMovie) o;

    return premiumPrice != null ? premiumPrice.equals(that.premiumPrice) : that.premiumPrice == null;

  }

  @Override
  public int hashCode()
  {
    return premiumPrice != null ? premiumPrice.hashCode() : 0;
  }
}
