import com.nerdkapp.videorentalstore.domain.Price;
import com.nerdkapp.videorentalstore.domain.RecentMovie;
import com.nerdkapp.videorentalstore.domain.RentalShop;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertEquals;

public class RentAMovieTest
{

  RentalShop rentalShop = new RentalShop();

  @Test
  public void rent_a_new_movie() throws Exception
  {
    RecentMovie recentMovie = new RecentMovie("Matrix");
    int numberOfDays = 1;

    Price price = rentalShop.rent(recentMovie, numberOfDays);

    Price expectedPrice = new Price(
        new BigDecimal("40.00").multiply(new BigDecimal(numberOfDays)),
        Currency.getInstance("SEK"));

    assertEquals(expectedPrice, price);
  }

  @Test
  public void rent_a_regular_movie() throws Exception
  {
    RecentMovie recentMovie = new RecentMovie("Matrix");
    int numberOfDays = 5;

    Price price = rentalShop.rent(recentMovie, numberOfDays);

    Price expectedPrice = new Price(
        new BigDecimal("90.00").multiply(new BigDecimal(numberOfDays)),
        Currency.getInstance("SEK"));

    assertEquals(expectedPrice, price);
  }
}
