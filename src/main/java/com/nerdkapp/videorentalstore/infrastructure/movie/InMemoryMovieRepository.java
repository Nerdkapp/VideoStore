package com.nerdkapp.videorentalstore.infrastructure.movie;

import com.nerdkapp.videorentalstore.domain.movies.Movie;
import com.nerdkapp.videorentalstore.domain.movies.MovieNotFoundException;
import com.nerdkapp.videorentalstore.domain.movies.MoviesRepository;
import com.nerdkapp.videorentalstore.domain.movies.pricing.OldMovie;
import com.nerdkapp.videorentalstore.domain.movies.pricing.PremiumMovie;
import com.nerdkapp.videorentalstore.domain.movies.pricing.RegularMovie;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryMovieRepository implements MoviesRepository
{
  private final Map<String, Movie> moviesDB;


  public InMemoryMovieRepository()
  {
    moviesDB = new HashMap<>();
    moviesDB.put("Matrix 11", new Movie("Matrix 11", MovieTypeFactory.premiumMovie()));
    moviesDB.put("Spiderman", new Movie("Spiderman", MovieTypeFactory.regularMovie()));
    moviesDB.put("Spiderman 2", new Movie("Spiderman 2", MovieTypeFactory.regularMovie()));
    moviesDB.put("Out of Africa", new Movie("Out of Africa", MovieTypeFactory.oldMovie()));
  }

  public InMemoryMovieRepository(Map<String, Movie> moviesDB)
  {
    this.moviesDB = moviesDB;
  }

  @Override
  public List<Movie> findMovies(List<String> movies)
  {
    List<Movie> moviesFound = new ArrayList<>();

    for (String movie : movies)
    {
      Movie movieFound = moviesDB.get(movie);

      if (movieFound == null)
        throw new MovieNotFoundException();

      moviesFound.add(movieFound);
    }

    return moviesFound;
  }
}
