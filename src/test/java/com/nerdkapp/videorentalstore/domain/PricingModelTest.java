package com.nerdkapp.videorentalstore.domain;

import com.nerdkapp.videorentalstore.domain.movies.pricing.OldMoviePricing;
import com.nerdkapp.videorentalstore.domain.movies.pricing.PremiumMoviePricing;
import com.nerdkapp.videorentalstore.domain.movies.pricing.RegularMoviePricing;
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
  public void premium_movies_returned_the_first_day() throws Exception
  {
    PremiumMoviePricing pricing = new PremiumMoviePricing();

    assertEquals(new BigDecimal("40.00"), pricing.calculatePrice(0));
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
