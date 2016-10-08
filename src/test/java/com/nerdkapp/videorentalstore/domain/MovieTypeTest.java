package com.nerdkapp.videorentalstore.domain;

import com.nerdkapp.videorentalstore.domain.movies.pricing.OldMovie;
import com.nerdkapp.videorentalstore.domain.movies.pricing.PremiumMovie;
import com.nerdkapp.videorentalstore.domain.movies.pricing.RegularMovie;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class MovieTypeTest
{
  @Test
  public void premium_movies() throws Exception
  {
    PremiumMovie pricing = new PremiumMovie();

    assertEquals(new BigDecimal("200.00"), pricing.calculatePrice(5));
  }

  @Test
  public void premium_movies_returned_the_first_day() throws Exception
  {
    PremiumMovie pricing = new PremiumMovie();

    assertEquals(new BigDecimal("40.00"), pricing.calculatePrice(0));
  }

  @Test
  public void regular_movies_not_more_than_3_days() throws Exception
  {
    RegularMovie pricing = new RegularMovie();

    assertEquals(new BigDecimal("30.00"), pricing.calculatePrice(1));
    assertEquals(new BigDecimal("30.00"), pricing.calculatePrice(3));
  }

  @Test
  public void regular_movies_more_than_3_days() throws Exception
  {
    RegularMovie pricing = new RegularMovie();

    assertEquals(new BigDecimal("90.00"), pricing.calculatePrice(5));
  }

  @Test
  public void old_movies_not_more_than_5_days() throws Exception
  {
    OldMovie pricing = new OldMovie();

    assertEquals(new BigDecimal("30.00"), pricing.calculatePrice(1));
    assertEquals(new BigDecimal("30.00"), pricing.calculatePrice(3));
    assertEquals(new BigDecimal("30.00"), pricing.calculatePrice(5));
  }

  @Test
  public void old_movies_more_than_5_days() throws Exception
  {
    OldMovie pricing = new OldMovie();

    assertEquals(new BigDecimal("60.00"), pricing.calculatePrice(6));
  }
}
