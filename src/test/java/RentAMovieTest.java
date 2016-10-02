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

  Price premiumPrice = new Price(new BigDecimal("40.00"), Currency.getInstance("SEK"));

  @Test
  public void rent_a_new_movie() throws Exception
  {
    RecentMovie recentMovie = new RecentMovie("Matrix");
    int numberOfDays = 4;

    Price price = rentalShop.rent(recentMovie, numberOfDays);

    Price expectedPrice = new Price(
        new BigDecimal("40.00").multiply(new BigDecimal(numberOfDays)),
        Currency.getInstance("SEK"));

    assertEquals(expectedPrice, price);
  }
}
