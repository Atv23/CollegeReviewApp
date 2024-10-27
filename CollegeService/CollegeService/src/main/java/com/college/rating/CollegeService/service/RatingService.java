package com.college.rating.CollegeService.service;

import com.college.rating.CollegeService.entity.Rating;
import jakarta.ws.rs.Path;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="RATINGSERVICE")
public interface RatingService {

    @GetMapping("/ratings/college/{collegeId}")
    List<Rating> getAllRatingsOfCollege(@PathVariable String collegeId);

    @DeleteMapping("/ratings/college/{collegeId}")
    void deleteAllRatingsOfCollege(@PathVariable String collegeId);
}
