package com.nerdkapp.videorentalstore.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Currency;

@Configuration
public class ShopConfiguration
{

  @Bean
  public Currency defaultShopCurrency()
  {
    return Currency.getInstance("SEK");
  }
}
