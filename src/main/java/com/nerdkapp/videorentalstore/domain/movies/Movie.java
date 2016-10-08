package com.nerdkapp.videorentalstore.domain.movies;

import com.nerdkapp.videorentalstore.domain.movies.pricing.MovieType;

public class Movie
{
  private final String title;

  private final MovieType movieType;

  public Movie(String title, MovieType movieType)
  {
    this.title = title;
    this.movieType = movieType;
  }

  public String getTitle()
  {
    return title;
  }

  public MovieType getMovieType(){ return movieType; }

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }

    Movie movie = (Movie) o;

    if (title != null ? !title.equals(movie.title) : movie.title != null)
    {
      return false;
    }
    return movieType != null ? movieType.equals(movie.movieType) : movie.movieType == null;

  }

  @Override
  public int hashCode()
  {
    int result = title != null ? title.hashCode() : 0;
    result = 31 * result + (movieType != null ? movieType.hashCode() : 0);
    return result;
  }

  @Override
  public String toString()
  {
    final StringBuffer sb = new StringBuffer("Movie{");
    sb.append("title='").append(title).append('\'');
    sb.append(", pricingModel=").append(movieType);
    sb.append('}');
    return sb.toString();
  }
}
