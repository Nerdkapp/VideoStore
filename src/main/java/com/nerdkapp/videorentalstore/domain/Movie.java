package com.nerdkapp.videorentalstore.domain;

import com.nerdkapp.videorentalstore.domain.pricing.PricingModel;

public class Movie
{
  private final String title;

  private final PricingModel pricingModel;

  public Movie(String title, PricingModel pricingModel)
  {
    this.title = title;
    this.pricingModel = pricingModel;
  }

  public String getTitle()
  {
    return title;
  }

  public PricingModel getPricingModel(){ return pricingModel; }

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
    return pricingModel != null ? pricingModel.equals(movie.pricingModel) : movie.pricingModel == null;

  }

  @Override
  public int hashCode()
  {
    int result = title != null ? title.hashCode() : 0;
    result = 31 * result + (pricingModel != null ? pricingModel.hashCode() : 0);
    return result;
  }

  @Override
  public String toString()
  {
    final StringBuffer sb = new StringBuffer("Movie{");
    sb.append("title='").append(title).append('\'');
    sb.append(", pricingModel=").append(pricingModel);
    sb.append('}');
    return sb.toString();
  }
}
