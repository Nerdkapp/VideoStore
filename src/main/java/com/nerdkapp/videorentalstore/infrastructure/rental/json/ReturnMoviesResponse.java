package com.nerdkapp.videorentalstore.infrastructure.rental.json;

import java.math.BigDecimal;
import java.util.Currency;

public class ReturnMoviesResponse
{
  private BigDecimal amountToPay;
  private Currency currency;

  public ReturnMoviesResponse()
  {
  }

  public ReturnMoviesResponse(BigDecimal amountToPay, Currency currency)
  {
    this.amountToPay = amountToPay;
    this.currency = currency;
  }

  @Override
  public String toString()
  {
    final StringBuffer sb = new StringBuffer("ReturnMoviesResponse{");
    sb.append("amountToPay=").append(amountToPay);
    sb.append(", currency=").append(currency);
    sb.append('}');
    return sb.toString();
  }

  public BigDecimal getAmountToPay()
  {
    return amountToPay;
  }

  public Currency getCurrency()
  {
    return currency;
  }
}
