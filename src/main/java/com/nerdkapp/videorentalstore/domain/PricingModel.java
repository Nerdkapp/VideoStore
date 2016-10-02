package com.nerdkapp.videorentalstore.domain;

public interface PricingModel
{
  public Price calculatePrice(Integer daysOfRental);
}
