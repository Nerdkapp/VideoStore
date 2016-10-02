package com.nerdkapp.videorentalstore.domain;

public class Movie
{
  private String title;

  private PricingModel pricingModel;

  public Movie(String title)
  {
    this.title = title;
  }

  public String getTitle()
  {
    return title;
  }
}
