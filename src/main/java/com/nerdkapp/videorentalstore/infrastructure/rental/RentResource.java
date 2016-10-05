package com.nerdkapp.videorentalstore.infrastructure.rental;

import com.nerdkapp.videorentalstore.domain.Price;
import com.nerdkapp.videorentalstore.domain.rental.RentalReceipt;
import com.nerdkapp.videorentalstore.domain.rental.RentalShop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    RentalReceipt rentalReceipt = rentalShop.rent(moviesToRent, rentalRequest.getStartRentalDate(), rentalRequest.getEndRentalData());

    return new RentalResponse(rentalReceipt.getRentalId(), rentalReceipt.getPrice().getAmount(), rentalReceipt.getPrice().getCurrency());
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
    private LocalDate startRentalDate;
    private LocalDate endRentalData;

    public RentalRequest()
    {
    }

    public RentalRequest(List<MovieRequest> movies, LocalDate startRentalDate, LocalDate endRentalData)
    {
      this.movies = movies;
      this.startRentalDate = startRentalDate;
      this.endRentalData = endRentalData;
    }

    public List<MovieRequest> getMovies()
    {
      return movies;
    }

    public void setMovies(List<MovieRequest> movies)
    {
      this.movies = movies;
    }

    public LocalDate getStartRentalDate()
    {
      return startRentalDate;
    }

    public LocalDate getEndRentalData()
    {
      return endRentalData;
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
