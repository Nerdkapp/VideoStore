package com.nerdkapp.videorentalstore.infrastructure.rental.pricing;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class OldMoviePricingTest
{
  @Test
  public void calculate_price_for_less_than_five_days() throws Exception
  {
    OldMoviePricing pricing = new OldMoviePricing();

    assertEquals(new BigDecimal("30.00"), pricing.calculatePrice(1));
    assertEquals(new BigDecimal("30.00"), pricing.calculatePrice(3));
    assertEquals(new BigDecimal("30.00"), pricing.calculatePrice(5));
  }

  @Test
  public void calculate_price_for_more_than_three_days() throws Exception
  {
    OldMoviePricing pricing = new OldMoviePricing();

    assertEquals(new BigDecimal("60.00"), pricing.calculatePrice(6));
  }
}