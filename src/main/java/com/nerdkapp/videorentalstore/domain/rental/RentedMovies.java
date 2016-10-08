package com.nerdkapp.videorentalstore.domain.rental;

import com.nerdkapp.videorentalstore.domain.movies.Movie;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class RentedMovies
{
  private final UUID rentalId;
  private final List<Movie> movies;
  private final LocalDate rentalDate;
  private final LocalDate expectedReturnDate;

  public RentedMovies(UUID rentalId, List<Movie> movies, LocalDate rentalDate, LocalDate expectedReturnDate)
  {
    this.rentalId = rentalId;
    this.movies = movies;
    this.rentalDate = rentalDate;
    this.expectedReturnDate = expectedReturnDate;
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

  public LocalDate getExpectedReturnDate()
  {
    return expectedReturnDate;
  }

  @Override
  public String toString()
  {
    final StringBuffer sb = new StringBuffer("RentedMovies{");
    sb.append("rentalId=").append(rentalId);
    sb.append(", movies=").append(movies);
    sb.append(", rentalDate=").append(rentalDate);
    sb.append(", expectedReturnDate=").append(expectedReturnDate);
    sb.append('}');
    return sb.toString();
  }
}
