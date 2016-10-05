package com.nerdkapp.videorentalstore.infrastructure.rental.movie;

import com.nerdkapp.videorentalstore.domain.movies.Movie;
import com.nerdkapp.videorentalstore.domain.movies.RentedMovies;
import com.nerdkapp.videorentalstore.domain.movies.MovieNotFoundException;
import com.nerdkapp.videorentalstore.domain.movies.pricing.OldMoviePricing;
import com.nerdkapp.videorentalstore.domain.movies.pricing.PremiumMoviePricing;
import com.nerdkapp.videorentalstore.domain.movies.pricing.RegularMoviePricing;
import com.nerdkapp.videorentalstore.infrastructure.rental.InMemoryRentalRepository;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InMemoryRentalRepositoryTest
{
  InMemoryRentalRepository repository;

  @Before
  public void setUp() throws Exception
  {
    Map<String, Movie> movieDB = new HashMap<>();
    movieDB.put("Matrix 11", new Movie("Matrix 11", new PremiumMoviePricing()));
    movieDB.put("Spiderman", new Movie("Spiderman", new RegularMoviePricing()));
    movieDB.put("Spiderman 2", new Movie("Spiderman 2", new RegularMoviePricing()));
    movieDB.put("Out of Africa", new Movie("Out of Africa", new OldMoviePricing()));
    repository = new InMemoryRentalRepository(movieDB);
  }

  @Test
  public void find_a_movie() throws Exception
  {
    Movie movie = repository.findMovie("Spiderman");

    assertEquals("Spiderman", movie.getTitle());
    assertEquals(new RegularMoviePricing(), movie.getPricingModel());
  }

  @Test(expected = MovieNotFoundException.class)
  public void movie_not_found() throws Exception
  {
    repository.findMovie("Not existent movie");
  }

  @Test
  public void rent_a_movie() throws Exception
  {
    List<Movie> moviesToRent = Arrays.asList(new Movie("Spiderman", new RegularMoviePricing()));

    UUID rentalId = repository.rentMovies(moviesToRent, tomorrow());
    assertNotNull(rentalId);
  }

  @Test
  public void retrieve_rented_movies() throws Exception
  {
    List<Movie> moviesToRent = Arrays.asList(new Movie("Spiderman", new RegularMoviePricing()));

    UUID rentalId = repository.rentMovies(moviesToRent, tomorrow());
    RentedMovies retrievedMovies = repository.retrieveRentedMovies(rentalId);
    assertEquals(moviesToRent, retrievedMovies.getMovies());
  }

  private LocalDate tomorrow()
  {
    return LocalDate.now().plus(1L, ChronoUnit.DAYS);
  }
}