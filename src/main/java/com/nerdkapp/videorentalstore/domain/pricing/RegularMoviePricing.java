package com.nerdkapp.videorentalstore.domain.pricing;

import com.nerdkapp.videorentalstore.domain.pricing.PricingModel;

import java.math.BigDecimal;

public class RegularMoviePricing implements PricingModel
{
  BigDecimal basicPrice = new BigDecimal("30.00");

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

    RegularMoviePricing that = (RegularMoviePricing) o;

    return basicPrice != null ? basicPrice.equals(that.basicPrice) : that.basicPrice == null;

  }

  @Override
  public int hashCode()
  {
    return basicPrice != null ? basicPrice.hashCode() : 0;
  }

  @Override
  public BigDecimal calculatePrice(Integer daysOfRental)
  {
    if(daysOfRental > 3)
      return basicPrice.add(basicPrice.multiply(new BigDecimal(daysOfRental-3)));
    else
      return basicPrice;
  }
}
