package com.nerdkapp.videorentalstore.infrastructure.rental.movie;

import com.nerdkapp.videorentalstore.domain.Movie;
import com.nerdkapp.videorentalstore.domain.RentedMovies;
import com.nerdkapp.videorentalstore.domain.pricing.OldMoviePricing;
import com.nerdkapp.videorentalstore.domain.pricing.PremiumMoviePricing;
import com.nerdkapp.videorentalstore.domain.pricing.RegularMoviePricing;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InMemoryMovieRepositoryTest
{
  InMemoryMovieRepository repository;

  @Before
  public void setUp() throws Exception
  {
    Map<String, Movie> movieDB = new HashMap<>();
    movieDB.put("Matrix 11", new Movie("Matrix 11", new PremiumMoviePricing()));
    movieDB.put("Spiderman", new Movie("Spiderman", new RegularMoviePricing()));
    movieDB.put("Spiderman 2", new Movie("Spiderman 2", new RegularMoviePricing()));
    movieDB.put("Out of Africa", new Movie("Out of Africa", new OldMoviePricing()));
    repository = new InMemoryMovieRepository(movieDB);
  }

  @Test
  public void find_a_movie() throws Exception
  {
    Movie movie = repository.findMovie("Spiderman");

    assertEquals("Spiderman", movie.getTitle());
    assertEquals(new RegularMoviePricing(), movie.getPricingModel());
  }

  @Test
  public void rent_a_movie() throws Exception
  {
    List<Movie> moviesToRent = Arrays.asList(new Movie("Spiderman", new RegularMoviePricing()));

    UUID rentalId = repository.rentMovies(moviesToRent);
    assertNotNull(rentalId);
  }

  @Test
  public void retrieve_rented_movies() throws Exception
  {
    List<Movie> moviesToRent = Arrays.asList(new Movie("Spiderman", new RegularMoviePricing()));

    UUID rentalId = repository.rentMovies(moviesToRent);
    RentedMovies retrievedMovies = repository.retrieveRentedMovies(rentalId);
    assertEquals(moviesToRent, retrievedMovies.getMovies());
  }
}