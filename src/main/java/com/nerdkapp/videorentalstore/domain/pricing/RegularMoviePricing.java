package com.nerdkapp.videorentalstore.domain.pricing;

import com.nerdkapp.videorentalstore.domain.pricing.PricingModel;

import java.math.BigDecimal;

public class RegularMoviePricing implements PricingModel
{
  BigDecimal basicPrice = new BigDecimal("30.00");

  @Override
  public BigDecimal calculatePrice(Integer daysOfRental)
  {
    if(daysOfRental > 3)
      return basicPrice.add(basicPrice.multiply(new BigDecimal(daysOfRental-3)));
    else
      return basicPrice;
  }
}
