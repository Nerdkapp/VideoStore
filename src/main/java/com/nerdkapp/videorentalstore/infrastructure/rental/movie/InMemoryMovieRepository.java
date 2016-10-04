package com.nerdkapp.videorentalstore.infrastructure.rental.movie;

import com.nerdkapp.videorentalstore.domain.Movie;
import com.nerdkapp.videorentalstore.domain.RentalRepository;
import com.nerdkapp.videorentalstore.domain.RentedMovies;
import com.nerdkapp.videorentalstore.domain.pricing.OldMoviePricing;
import com.nerdkapp.videorentalstore.domain.pricing.PremiumMoviePricing;
import com.nerdkapp.videorentalstore.domain.pricing.RegularMoviePricing;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class InMemoryMovieRepository implements RentalRepository
{
  private Map<String, Movie> movies;
  private Map<UUID, RentedMovies> rentedMovies;

  public InMemoryMovieRepository()
  {
    movies = new HashMap<>();
    movies.put("Matrix 11", new Movie("Matrix 11", new PremiumMoviePricing()));
    movies.put("Spiderman", new Movie("Spiderman", new RegularMoviePricing()));
    movies.put("Spiderman 2", new Movie("Spiderman 2", new RegularMoviePricing()));
    movies.put("Out of Africa", new Movie("Out of Africa", new OldMoviePricing()));

    rentedMovies = new HashMap<>();
  }

  public InMemoryMovieRepository(Map<String, Movie> movies)
  {
    this.movies = movies;
    this.rentedMovies = new HashMap<>();
  }

  @Override
  public RentedMovies retrieveRentedMovies(UUID rentalId)
  {
    return rentedMovies.get(rentalId);
  }

  @Override
  public Movie findMovie(String movie)
  {
    return movies.get(movie);
  }

  @Override
  public UUID rentMovies(List<Movie> movies)
  {
    UUID id = UUID.randomUUID();
    rentedMovies.put(id, new RentedMovies(id, movies, LocalDate.now()));
    return id;
  }
}
