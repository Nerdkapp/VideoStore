package com.nerdkapp.videorentalstore.infrastructure.movie;

import com.nerdkapp.videorentalstore.domain.movies.Movie;
import com.nerdkapp.videorentalstore.domain.movies.MovieNotFoundException;
import com.nerdkapp.videorentalstore.domain.movies.MoviesRepository;
import com.nerdkapp.videorentalstore.domain.movies.pricing.OldMovie;
import com.nerdkapp.videorentalstore.domain.movies.pricing.PremiumMovie;
import com.nerdkapp.videorentalstore.domain.movies.pricing.RegularMovie;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class InMemoryMovieRepositoryTest
{
  MoviesRepository repository;

  @Before
  public void setUp() throws Exception
  {
    Map<String, Movie> movieDB = new HashMap<>();
    movieDB.put("Matrix 11", new Movie("Matrix 11", new PremiumMovie()));
    movieDB.put("Spiderman", new Movie("Spiderman", new RegularMovie()));
    movieDB.put("Spiderman 2", new Movie("Spiderman 2", new RegularMovie()));
    movieDB.put("Out of Africa", new Movie("Out of Africa", new OldMovie()));
    repository = new InMemoryMovieRepository(movieDB);
  }

  @Test
  public void find_a_movie() throws Exception
  {
    List<Movie> movie = repository.findMovies(Arrays.asList("Spiderman"));

    assertEquals("Spiderman", movie.get(0).getTitle());
    assertEquals(new RegularMovie(), movie.get(0).getMovieType());
  }

  @Test(expected = MovieNotFoundException.class)
  public void movie_not_found() throws Exception
  {
    repository.findMovies(Arrays.asList("Not existent movie"));
  }
}