package com.nerdkapp.videorentalstore.domain;

import java.math.BigDecimal;

public interface PricingModel
{
  public BigDecimal calculatePrice(Integer daysOfRental);
}
