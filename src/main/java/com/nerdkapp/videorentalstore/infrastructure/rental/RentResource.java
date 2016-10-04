package com.nerdkapp.videorentalstore.infrastructure.rental;

import com.nerdkapp.videorentalstore.domain.Movie;
import com.nerdkapp.videorentalstore.domain.Price;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/rent")
public class RentResource
{
  private static final Logger LOGGER = LoggerFactory.getLogger(RentResource.class);

  @RequestMapping(value = "/{userId}", method = RequestMethod.POST)
  public RentalResponse rent(@PathVariable("userId") String userId, @RequestBody RentalRequest rentalRequest){
    LOGGER.info("User {} asked to rent {}", userId, rentalRequest);
    return null;
  }

  public static class RentalRequest
  {
    private List<MovieRequest> movies;

    public RentalRequest()
    {
    }

    public List<MovieRequest> getMovies()
    {
      return movies;
    }

    public void setMovies(List<MovieRequest> movies)
    {
      this.movies = movies;
    }

    @Override
    public String toString()
    {
      final StringBuffer sb = new StringBuffer("RentalRequest{");
      sb.append("movies=").append(movies);
      sb.append('}');
      return sb.toString();
    }
  }

  public static class MovieRequest
  {
    private String title;

    public MovieRequest()
    {
    }

    public String getTitle()
    {
      return title;
    }

    public void setTitle(String title)
    {
      this.title = title;
    }

    @Override
    public String toString()
    {
      final StringBuffer sb = new StringBuffer("MovieRequest{");
      sb.append("title='").append(title).append('\'');
      sb.append('}');
      return sb.toString();
    }
  }

  public static class RentalResponse
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
      sb.append(", amountToPay=").append(amountToPay);
      sb.append(", currency=").append(currency);
      sb.append('}');
      return sb.toString();
    }
  }
}
