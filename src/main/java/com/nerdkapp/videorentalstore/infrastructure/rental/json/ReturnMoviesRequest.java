package com.nerdkapp.videorentalstore.infrastructure.rental.json;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class ReturnMoviesRequest
{
  @NotNull
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
