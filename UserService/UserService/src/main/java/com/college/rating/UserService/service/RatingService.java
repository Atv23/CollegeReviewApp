package com.college.rating.UserService.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="RATINGSERVICE")
public interface RatingService {

    @DeleteMapping("ratings/user/{userId}")
    void deleteRatingsOfUser(@PathVariable String userId);
}
