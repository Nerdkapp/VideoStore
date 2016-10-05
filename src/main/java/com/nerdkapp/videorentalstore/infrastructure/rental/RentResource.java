package com.nerdkapp.videorentalstore.infrastructure.rental;

import com.nerdkapp.videorentalstore.domain.Price;
import com.nerdkapp.videorentalstore.domain.rental.RentalReceipt;
import com.nerdkapp.videorentalstore.domain.rental.RentalShop;
import com.nerdkapp.videorentalstore.infrastructure.rental.json.RentalRequest;
import com.nerdkapp.videorentalstore.infrastructure.rental.json.RentalResponse;
import com.nerdkapp.videorentalstore.infrastructure.rental.json.ReturnMoviesRequest;
import com.nerdkapp.videorentalstore.infrastructure.rental.json.ReturnMoviesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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

    RentalReceipt rentalReceipt = rentalShop.rent(moviesToRent,
        convertToLocalDate(rentalRequest.getStartRentalDate()),
        convertToLocalDate(rentalRequest.getEndRentalDate()));

    return new RentalResponse(rentalReceipt.getRentalId(), rentalReceipt.getPrice().getAmount(), rentalReceipt.getPrice().getCurrency());
  }

  @RequestMapping(value = "/{userId}/{rentalId}/", method = RequestMethod.PUT)
  public ReturnMoviesResponse returnMovies(
      @PathVariable("userId") String userId,
      @PathVariable UUID rentalId,
      @RequestBody ReturnMoviesRequest returnMoviesRequest){
    LOGGER.info("User {} returned rented movies for rental id: {}", userId, rentalId);
    Price price = rentalShop.returnMovies(rentalId, convertToLocalDate(returnMoviesRequest.getReturnDate()));

    return new ReturnMoviesResponse(price.getAmount(), price.getCurrency());
  }

  private LocalDate convertToLocalDate(Date date)
  {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }
}
