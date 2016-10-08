package com.nerdkapp.videorentalstore.infrastructure.rental;

import com.nerdkapp.videorentalstore.domain.rental.RentalNotFoundException;
import com.nerdkapp.videorentalstore.domain.movies.Movie;
import com.nerdkapp.videorentalstore.domain.rental.RentalRepository;
import com.nerdkapp.videorentalstore.domain.movies.RentedMovies;
import com.nerdkapp.videorentalstore.domain.movies.MovieNotFoundException;
import com.nerdkapp.videorentalstore.domain.movies.pricing.OldMoviePricing;
import com.nerdkapp.videorentalstore.domain.movies.pricing.PremiumMoviePricing;
import com.nerdkapp.videorentalstore.domain.movies.pricing.RegularMoviePricing;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class InMemoryRentalRepository implements RentalRepository
{
  private Map<String, Movie> movies;
  private Map<UUID, RentedMovies> rentedMovies;

  public InMemoryRentalRepository()
  {
    movies = new HashMap<>();
    movies.put("Matrix 11", new Movie("Matrix 11", new PremiumMoviePricing()));
    movies.put("Spiderman", new Movie("Spiderman", new RegularMoviePricing()));
    movies.put("Spiderman 2", new Movie("Spiderman 2", new RegularMoviePricing()));
    movies.put("Out of Africa", new Movie("Out of Africa", new OldMoviePricing()));

    rentedMovies = new HashMap<>();
  }

  public InMemoryRentalRepository(Map<String, Movie> movies)
  {
    this.movies = movies;
    this.rentedMovies = new HashMap<>();
  }

  @Override
  public RentedMovies retrieveRentedMovies(UUID rentalId)
  {
    RentedMovies rental = rentedMovies.get(rentalId);

    if(rental == null)
      throw new RentalNotFoundException();

    return rental;
  }

  @Override
  public Movie findMovies(String movie)
  {
    Movie movieFound = movies.get(movie);

    if(movieFound == null)
      throw new MovieNotFoundException();

    return movieFound;
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
