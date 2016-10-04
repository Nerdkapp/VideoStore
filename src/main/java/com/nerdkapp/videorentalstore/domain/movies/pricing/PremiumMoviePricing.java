package com.nerdkapp.videorentalstore.domain.movies.pricing;

import java.math.BigDecimal;

public class PremiumMoviePricing implements PricingModel
{
  BigDecimal premiumPrice = new BigDecimal("40.00");

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

    PremiumMoviePricing that = (PremiumMoviePricing) o;

    return premiumPrice != null ? premiumPrice.equals(that.premiumPrice) : that.premiumPrice == null;

  }

  @Override
  public int hashCode()
  {
    return premiumPrice != null ? premiumPrice.hashCode() : 0;
  }

  @Override
  public BigDecimal calculatePrice(Integer daysOfRental)
  {
    if(daysOfRental > 1)
      return premiumPrice.multiply(new BigDecimal(daysOfRental));
    else
      return premiumPrice;
  }
}
