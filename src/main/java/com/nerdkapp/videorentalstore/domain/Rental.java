package com.nerdkapp.videorentalstore.domain;

import java.math.BigDecimal;

public class Rental
{
  private final Movie movie;
  private final int daysOfRental;

  public Rental(Movie movie, int daysOfRental)
  {
    this.movie = movie;
    this.daysOfRental = daysOfRental;
  }

  public BigDecimal calculate()
  {
    return movie.getPricingModel().calculatePrice(daysOfRental);
  }
}
