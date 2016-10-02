package com.nerdkapp.videorentalstore.infrastructure.rental.pricing;

import com.nerdkapp.videorentalstore.infrastructure.rental.pricing.PremiumMoviePricing;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

//New releases – Price is <premium price> times number of days rented.

public class PremiumMoviePricingTest
{
  @Test
  public void calculate_price() throws Exception
  {
    PremiumMoviePricing pricing = new PremiumMoviePricing();

    assertEquals(new BigDecimal("200.00"), pricing.calculatePrice(5));
  }
}