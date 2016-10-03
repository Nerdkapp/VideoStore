package com.nerdkapp.videorentalstore.domain;

import com.nerdkapp.videorentalstore.infrastructure.rental.pricing.PremiumMoviePricing;

import java.math.BigDecimal;

public class Movie
{
  private final String title;

  private final PricingModel pricingModel;

  public Movie(String title, PricingModel pricingModel)
  {
    this.title = title;
    this.pricingModel = pricingModel;
  }

  public String getTitle()
  {
    return title;
  }

  public BigDecimal calculatePrice(int numberOfDaysToRent)
  {
    return pricingModel.calculatePrice(numberOfDaysToRent);
  }
}
