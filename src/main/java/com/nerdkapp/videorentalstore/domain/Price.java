package com.nerdkapp.videorentalstore.domain;

import java.math.BigDecimal;
import java.util.Currency;

public class Price
{
  private BigDecimal amount;
  private Currency currency;

  public Price(BigDecimal amount, Currency currency)
  {
    this.amount = amount;
    this.currency = currency;
  }

  public BigDecimal getAmount()
  {
    return amount;
  }

  public Currency getCurrency()
  {
    return currency;
  }

  @Override
  public String toString()
  {
    final StringBuffer sb = new StringBuffer("Price{");
    sb.append("amount=").append(amount);
    sb.append(", currency=").append(currency);
    sb.append('}');
    return sb.toString();
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }

    Price price = (Price) o;

    if (!amount.equals(price.amount))
    {
      return false;
    }
    return currency.equals(price.currency);

  }

  @Override
  public int hashCode()
  {
    int result = amount.hashCode();
    result = 31 * result + currency.hashCode();
    return result;
  }
}
