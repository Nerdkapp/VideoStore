package com.nerdkapp.videorentalstore.infrastructure.rental;

import com.nerdkapp.videorentalstore.domain.movies.Movie;
import com.nerdkapp.videorentalstore.domain.rental.RentedMovies;
import com.nerdkapp.videorentalstore.domain.rental.RentalNotFoundException;
import com.nerdkapp.videorentalstore.domain.rental.RentalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class InMemoryRentalRepository implements RentalRepository
{
  private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryRentalRepository.class);
  private Map<UUID, RentedMovies> rentedMovies;

  public InMemoryRentalRepository()
  {
    rentedMovies = new HashMap<>();
  }

  @Override
  public RentedMovies findRental(UUID rentalId)
  {
    RentedMovies rental = rentedMovies.get(rentalId);

    if(rental == null)
    {
      LOGGER.warn("No rental found for {}", rentalId);
      throw new RentalNotFoundException();
    }

    return rental;
  }

  @Override
  public UUID rentMovies(List<Movie> movies, LocalDate expectedReturnDate)
  {
    UUID id = UUID.randomUUID();
    rentedMovies.put(id, new RentedMovies(id, movies, LocalDate.now(), expectedReturnDate));
    return id;
  }

  @Override
  public void closeRental(UUID rentalId)
  {
    rentedMovies.remove(rentalId);
  }
}
