package com.nerdkapp.videorentalstore.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class RentedMovies
{
  private final UUID rentalId;
  private final List<Movie> movies;
  private final LocalDate rentalDate;

  public RentedMovies(UUID rentalId, List<Movie> movies, LocalDate rentalDate)
  {
    this.rentalId = rentalId;
    this.movies = movies;
    this.rentalDate = rentalDate;
  }

  public UUID getRentalId()
  {
    return rentalId;
  }

  public List<Movie> getMovies()
  {
    return movies;
  }

  public LocalDate getRentalDate()
  {
    return rentalDate;
  }

  @Override
  public String toString()
  {
    final StringBuffer sb = new StringBuffer("RentedMovies{");
    sb.append("rentalId=").append(rentalId);
    sb.append(", movies=").append(movies);
    sb.append(", rentalDate=").append(rentalDate);
    sb.append('}');
    return sb.toString();
  }
}
