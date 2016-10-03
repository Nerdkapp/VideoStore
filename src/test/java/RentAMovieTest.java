import com.nerdkapp.videorentalstore.domain.Price;
import com.nerdkapp.videorentalstore.domain.Movie;
import com.nerdkapp.videorentalstore.domain.Rental;
import com.nerdkapp.videorentalstore.domain.RentalShop;
import com.nerdkapp.videorentalstore.infrastructure.rental.pricing.PremiumMoviePricing;
import com.nerdkapp.videorentalstore.infrastructure.rental.pricing.RegularMoviePricing;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;

import static org.junit.Assert.assertEquals;

public class RentAMovieTest
{
  RentalShop rentalShop = new RentalShop(Currency.getInstance("SEK"));

  @Test
  public void rent_a_new_movie() throws Exception
  {
    Movie movie = new Movie("Matrix", new PremiumMoviePricing());
    int numberOfDays = 1;

    Price price = rentalShop.rent(movie, numberOfDays);

    Price expectedPrice = new Price( new BigDecimal("40.00"), Currency.getInstance("SEK"));

    assertEquals(expectedPrice, price);
  }

  @Test
  public void rent_a_regular_movie() throws Exception
  {
    Movie movie = new Movie("Matrix", new RegularMoviePricing());
    int numberOfDays = 5;

    Price price = rentalShop.rent(movie, numberOfDays);

    Price expectedPrice = new Price( new BigDecimal("90.00"), Currency.getInstance("SEK"));

    assertEquals(expectedPrice, price);
  }

  @Test
  public void rent_multiple_movies() throws Exception
  {
    Rental firstRental = new Rental(new Movie("Matrix", new PremiumMoviePricing()), 1);
    Rental secondRental = new Rental(new Movie("Spiderman 100", new RegularMoviePricing()), 5);

    Price price = rentalShop.rent(Arrays.asList(firstRental, secondRental));

    Price expectedPrice = new Price( new BigDecimal("130.00"), Currency.getInstance("SEK"));

    assertEquals(expectedPrice, price);
  }
}
