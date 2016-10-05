package com.nerdkapp.videorentalstore.infrastructure.rental.json;

import java.util.Date;

public class ReturnMoviesRequest
{
  private Date returnDate;

  public ReturnMoviesRequest()
  {
  }

  public ReturnMoviesRequest(Date returnDate)
  {
    this.returnDate = returnDate;
  }

  public Date getReturnDate()
  {
    return returnDate;
  }
}
