package com.nerdkapp.videorentalstore.domain.pricing;

import com.nerdkapp.videorentalstore.domain.pricing.PricingModel;

import java.math.BigDecimal;

public class PremiumMoviePricing implements PricingModel
{
  BigDecimal premiumPrice = new BigDecimal("40.00");

  @Override
  public BigDecimal calculatePrice(Integer daysOfRental)
  {
    return premiumPrice.multiply(new BigDecimal(daysOfRental));
  }
}
