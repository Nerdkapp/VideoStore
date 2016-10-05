package com.nerdkapp.videorentalstore.infrastructure.rental.json;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class RentalRequest
{
  @NotNull
  private List<Movie> movies;
  @NotNull
  private Date startRentalDate;
  @NotNull
  private Date endRentalDate;

  public RentalRequest()
  {
  }

  public RentalRequest(List<Movie> movies, Date startRentalDate, Date endRentalDate)
  {
    this.movies = movies;
    this.startRentalDate = startRentalDate;
    this.endRentalDate = endRentalDate;
  }

  public List<Movie> getMovies()
  {
    return movies;
  }

  public void setMovies(List<Movie> movies)
  {
    this.movies = movies;
  }

  public Date getStartRentalDate()
  {
    return startRentalDate;
  }

  public Date getEndRentalDate()
  {
    return endRentalDate;
  }

  @Override
  public String toString()
  {
    final StringBuffer sb = new StringBuffer("RentalRequest{");
    sb.append("movies=").append(movies);
    sb.append(", startRentalDate=").append(startRentalDate);
    sb.append(", endRentalDate=").append(endRentalDate);
    sb.append('}');
    return sb.toString();
  }
}
