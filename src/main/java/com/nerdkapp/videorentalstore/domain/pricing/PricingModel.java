package com.nerdkapp.videorentalstore.domain.pricing;

import java.math.BigDecimal;

public interface PricingModel
{
  public BigDecimal calculatePrice(Integer daysOfRental);
}
