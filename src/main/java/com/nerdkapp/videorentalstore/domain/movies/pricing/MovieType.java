package com.nerdkapp.videorentalstore.domain.movies.pricing;

import java.math.BigDecimal;

public interface MovieType
{
  BigDecimal calculatePrice(Integer daysOfRental);

  Integer getBonusPointsForRental();
}
