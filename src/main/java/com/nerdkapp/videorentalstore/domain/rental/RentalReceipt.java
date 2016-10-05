package com.nerdkapp.videorentalstore.domain.rental;

import com.nerdkapp.videorentalstore.domain.Price;

import java.util.UUID;

public class RentalReceipt
{
  private final UUID rentalId;
  private final Price price;

  public RentalReceipt(UUID rentalId, Price price)
  {
    this.rentalId = rentalId;
    this.price = price;
  }

  public UUID getRentalId()
  {
    return rentalId;
  }

  public Price getPrice()
  {
    return price;
  }
}
