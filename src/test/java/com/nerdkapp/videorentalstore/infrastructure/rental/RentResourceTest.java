package com.nerdkapp.videorentalstore.infrastructure.rental;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class RentResourceTest
{
  RentResource rentResource = new RentResource();

  @Test
  public void rent_a_movie() throws Exception
  {
    RentResource.RentalResponse rentalResponse = rentResource.rent("lcoccia", aRentalRequest());
    assertNotNull(rentalResponse.getRentalId());
  }

  @Test
  public void return_a_movie() throws Exception
  {
    RentResource.ReturnMoviesResponse rentalResponse = rentResource.returnMovies("lcoccia", UUID.randomUUID());

  }


  private RentResource.RentalRequest aRentalRequest()
  {
    List<RentResource.MovieRequest> movies = Arrays.asList(new RentResource.MovieRequest("Matrix"));
    return new RentResource.RentalRequest(movies);
  }
}