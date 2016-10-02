package com.nerdkapp.videorentalstore.infrastructure.rental.pricing;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class RegularMoviePricingTest
{
  @Test
  public void calculate_price_for_less_than_three_days() throws Exception
  {
    RegularMoviePricing pricing = new RegularMoviePricing();

    assertEquals(new BigDecimal("30.00"), pricing.calculatePrice(1));
    assertEquals(new BigDecimal("30.00"), pricing.calculatePrice(3));
  }

  @Test
  public void calculate_price_for_more_than_three_days() throws Exception
  {
    RegularMoviePricing pricing = new RegularMoviePricing();

    assertEquals(new BigDecimal("90.00"), pricing.calculatePrice(5));
  }
}