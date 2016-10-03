package com.nerdkapp.videorentalstore.infrastructure.rental.pricing;

import com.nerdkapp.videorentalstore.domain.pricing.OldMoviePricing;
import com.nerdkapp.videorentalstore.domain.pricing.PremiumMoviePricing;
import com.nerdkapp.videorentalstore.domain.pricing.RegularMoviePricing;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class PricingModelTest
{
  @Test
  public void premium_movies() throws Exception
  {
    PremiumMoviePricing pricing = new PremiumMoviePricing();

    assertEquals(new BigDecimal("200.00"), pricing.calculatePrice(5));
  }

  @Test
  public void regular_movies_not_more_than_3_days() throws Exception
  {
    RegularMoviePricing pricing = new RegularMoviePricing();

    assertEquals(new BigDecimal("30.00"), pricing.calculatePrice(1));
    assertEquals(new BigDecimal("30.00"), pricing.calculatePrice(3));
  }

  @Test
  public void regular_movies_more_than_3_days() throws Exception
  {
    RegularMoviePricing pricing = new RegularMoviePricing();

    assertEquals(new BigDecimal("90.00"), pricing.calculatePrice(5));
  }

  @Test
  public void old_movies_not_more_than_5_days() throws Exception
  {
    OldMoviePricing pricing = new OldMoviePricing();

    assertEquals(new BigDecimal("30.00"), pricing.calculatePrice(1));
    assertEquals(new BigDecimal("30.00"), pricing.calculatePrice(3));
    assertEquals(new BigDecimal("30.00"), pricing.calculatePrice(5));
  }

  @Test
  public void old_movies_more_than_5_days() throws Exception
  {
    OldMoviePricing pricing = new OldMoviePricing();

    assertEquals(new BigDecimal("60.00"), pricing.calculatePrice(6));
  }
}
