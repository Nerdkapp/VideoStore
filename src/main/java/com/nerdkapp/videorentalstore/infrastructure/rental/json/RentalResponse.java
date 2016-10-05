package com.nerdkapp.videorentalstore.infrastructure.rental.json;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public class RentalResponse
{
  private UUID rentalId;
  private BigDecimal amountToPay;
  private Currency currency;

  public RentalResponse()
  {
  }

  public RentalResponse(UUID rentalId, BigDecimal amountToPay, Currency currency)
  {
    this.rentalId = rentalId;
    this.amountToPay = amountToPay;
    this.currency = currency;
  }

  public UUID getRentalId()
  {
    return rentalId;
  }

  public BigDecimal getAmountToPay()
  {
    return amountToPay;
  }

  public Currency getCurrency()
  {
    return currency;
  }

  @Override
  public String toString()
  {
    final StringBuffer sb = new StringBuffer("RentalResponse{");
    sb.append("rentalId=").append(rentalId);
    sb.append('}');
    return sb.toString();
  }
}
