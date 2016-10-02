package com.nerdkapp.videorentalstore.infrastructure.rental.pricing;

import com.nerdkapp.videorentalstore.domain.PricingModel;

import java.math.BigDecimal;

public class OldMoviePricing implements PricingModel
{
  BigDecimal basicPrice = new BigDecimal("30.00");

  @Override
  public BigDecimal calculatePrice(Integer daysOfRental)
  {
    if(daysOfRental > 5)
      return basicPrice.add(basicPrice.multiply(new BigDecimal(daysOfRental - 5)));
    else
      return basicPrice;
  }
}
