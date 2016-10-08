package com.nerdkapp.videorentalstore.infrastructure.movie;

import com.nerdkapp.videorentalstore.domain.movies.pricing.MovieType;
import com.nerdkapp.videorentalstore.domain.movies.pricing.OldMovie;
import com.nerdkapp.videorentalstore.domain.movies.pricing.PremiumMovie;
import com.nerdkapp.videorentalstore.domain.movies.pricing.RegularMovie;

public class MovieTypeFactory
{
  private static final RegularMovie regularMovie = new RegularMovie();
  private static final PremiumMovie premiumMovie = new PremiumMovie();
  private static final OldMovie oldMovie = new OldMovie();

  public static MovieType regularMovie()
  {
    return regularMovie;
  }

  public static MovieType premiumMovie()
  {
    return premiumMovie;
  }

  public static MovieType oldMovie()
  {
    return oldMovie;
  }
}
