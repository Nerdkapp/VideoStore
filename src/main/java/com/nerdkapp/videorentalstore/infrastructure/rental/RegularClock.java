package com.nerdkapp.videorentalstore.infrastructure.rental;

import com.nerdkapp.videorentalstore.domain.Clock;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RegularClock implements Clock
{
  @Override
  public LocalDate now()
  {
    return LocalDate.now();
  }
}
