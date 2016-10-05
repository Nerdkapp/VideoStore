package com.nerdkapp.videorentalstore.infrastructure.rental.json;

import javax.validation.constraints.NotNull;

public class Movie
{
  @NotNull
  private String title;

  public Movie()
  {
  }

  public Movie(String title)
  {
    this.title = title;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  @Override
  public String toString()
  {
    final StringBuffer sb = new StringBuffer("MovieRequest{");
    sb.append("title='").append(title).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
