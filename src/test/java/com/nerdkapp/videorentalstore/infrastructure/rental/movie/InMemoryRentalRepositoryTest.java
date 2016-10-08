package com.nerdkapp.videorentalstore.infrastructure.rental.movie;

import com.nerdkapp.videorentalstore.domain.rental.RentalNotFoundException;
import com.nerdkapp.videorentalstore.domain.movies.Movie;
import com.nerdkapp.videorentalstore.domain.rental.RentedMovies;
import com.nerdkapp.videorentalstore.domain.movies.pricing.RegularMovie;
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
    repository = new InMemoryRentalRepository();
  }

  @Test
  public void rent_a_movie() throws Exception
  {
    List<Movie> moviesToRent = Arrays.asList(new Movie("Spiderman", new RegularMovie()));

    UUID rentalId = repository.rentMovies(moviesToRent, tomorrow());
    assertNotNull(rentalId);
  }

  @Test
  public void retrieve_rented_movies() throws Exception
  {
    List<Movie> moviesToRent = Arrays.asList(new Movie("Spiderman", new RegularMovie()));

    UUID rentalId = repository.rentMovies(moviesToRent, tomorrow());
    RentedMovies retrievedMovies = repository.findRental(rentalId);
    assertEquals(moviesToRent, retrievedMovies.getMovies());
  }

  @Test(expected = RentalNotFoundException.class)
  public void rental_not_found() throws Exception
  {
    repository.findRental(UUID.randomUUID());
  }

  @Test(expected = RentalNotFoundException.class)
  public void close_rental() throws Exception
  {
    List<Movie> moviesToRent = Arrays.asList(new Movie("Spiderman", new RegularMovie()));

    UUID rentalId = repository.rentMovies(moviesToRent, tomorrow());
    repository.closeRental(rentalId);
    repository.findRental(rentalId);
  }

  private LocalDate tomorrow()
  {
    return LocalDate.now().plus(1L, ChronoUnit.DAYS);
  }
}