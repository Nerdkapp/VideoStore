package com.nerdkapp.videorentalstore.domain;

import com.nerdkapp.videorentalstore.infrastructure.rental.pricing.PremiumMoviePricing;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class MovieTest
{
  @Test
  public void calculatePrice() throws Exception
  {
    Movie matrix = new Movie("Matrix", new PremiumMoviePricing());

    assertEquals(new BigDecimal("200.00"), matrix.calculatePrice(5));
  }
}