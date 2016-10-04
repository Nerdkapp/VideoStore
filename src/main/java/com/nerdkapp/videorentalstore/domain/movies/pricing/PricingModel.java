package com.nerdkapp.videorentalstore.domain.movies.pricing;

import java.math.BigDecimal;

public interface PricingModel
{
  public BigDecimal calculatePrice(Integer daysOfRental);
}
