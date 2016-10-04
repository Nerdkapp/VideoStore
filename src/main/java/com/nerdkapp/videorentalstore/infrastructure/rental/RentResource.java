package com.nerdkapp.videorentalstore.infrastructure.rental;

import com.nerdkapp.videorentalstore.domain.Price;
import com.nerdkapp.videorentalstore.domain.RentalShop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/rent")
public class RentResource
{
  private static final Logger LOGGER = LoggerFactory.getLogger(RentResource.class);

  private RentalShop rentalShop;

  @Autowired
  public RentResource(RentalShop rentalShop)
  {
    this.rentalShop = rentalShop;
  }

  @RequestMapping(value = "/{userId}", method = RequestMethod.POST)
  public RentalResponse rent(@PathVariable("userId") String userId, @RequestBody RentalRequest rentalRequest){
    LOGGER.info("User {} asked to rent {}", userId, rentalRequest);

    List<String> moviesToRent = rentalRequest.getMovies().stream().
        map(r -> r.getTitle()).
        collect(Collectors.toList());

    UUID rentalId = rentalShop.rent(moviesToRent);

    return new RentalResponse(rentalId);
  }

  @RequestMapping(value = "/{userId}/{rentalId}", method = RequestMethod.PUT)
  public ReturnMoviesResponse returnMovies(@PathVariable("userId") String userId, @PathVariable UUID rentalId){
    LOGGER.info("User {} returned rented movies for rental id: {}", userId, rentalId);
    Price price = rentalShop.returnMovies(rentalId);

    return new ReturnMoviesResponse(price.getAmount(), price.getCurrency());
  }

  public static class RentalRequest
  {
    private List<MovieRequest> movies;

    public RentalRequest()
    {
    }

    public RentalRequest(List<MovieRequest> movies)
    {
      this.movies = movies;
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

    public MovieRequest(String title)
    {
      this.title = title;
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

    public RentalResponse()
    {
    }

    public RentalResponse(UUID rentalId)
    {
      this.rentalId = rentalId;
    }

    public UUID getRentalId()
    {
      return rentalId;
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

  public static class ReturnMoviesResponse
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
}
