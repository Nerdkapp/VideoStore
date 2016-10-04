package com.nerdkapp.videorentalstore.infrastructure.rental.movie;

import com.nerdkapp.videorentalstore.domain.Movie;
import com.nerdkapp.videorentalstore.domain.RentalRepository;
import com.nerdkapp.videorentalstore.domain.RentedMovies;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InMemoryMovieRepository implements RentalRepository
{
  private Map<String, Movie> movies;
  private Map<UUID, RentedMovies> rentedMovies;

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
