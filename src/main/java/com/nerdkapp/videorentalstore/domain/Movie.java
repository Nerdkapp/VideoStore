package com.nerdkapp.videorentalstore.domain;

import com.nerdkapp.videorentalstore.domain.pricing.PricingModel;

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

  public PricingModel getPricingModel(){ return pricingModel; }
}
