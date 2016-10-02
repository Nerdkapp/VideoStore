package com.nerdkapp.videorentalstore.infrastructure.rental.pricing;

import com.nerdkapp.videorentalstore.domain.Price;
import com.nerdkapp.videorentalstore.domain.PricingModel;

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
