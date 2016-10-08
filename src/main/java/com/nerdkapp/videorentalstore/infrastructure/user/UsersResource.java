package com.nerdkapp.videorentalstore.infrastructure.user;

import com.nerdkapp.videorentalstore.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
public class UsersResource
{
  UserRepository userRepository;

  @Autowired
  public UsersResource(UserRepository userRepository)
  {
    this.userRepository = userRepository;
  }

  @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
  public GetBonusPointsResponse getPoints(@PathVariable("userId") String userId)
  {
    return new GetBonusPointsResponse(userRepository.getPoints(userId));
  }

  public static class GetBonusPointsResponse
  {
    Integer points;

    public GetBonusPointsResponse()
    {
    }

    public Integer getPoints()
    {
      return points;
    }

    public GetBonusPointsResponse(Integer points)
    {
      this.points = points;
    }
  }
}
