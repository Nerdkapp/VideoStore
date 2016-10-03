package com.nerdkapp.videorentalstore.domain;

import java.math.BigDecimal;

public class Rental
{
  private final Movie matrix;
  private final int daysOfRental;

  public Rental(Movie matrix, int daysOfRental)
  {
    this.matrix = matrix;
    this.daysOfRental = daysOfRental;
  }

  public BigDecimal calculate()
  {
    return this.matrix.calculatePrice(daysOfRental);
  }
}
