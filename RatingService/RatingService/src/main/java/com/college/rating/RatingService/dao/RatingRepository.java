package com.college.rating.RatingService.dao;

import com.college.rating.RatingService.entity.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends MongoRepository<Rating,String> {

    List<Rating> findByUserId(String userId);

    List<Rating> findByCollegeId(String collegeId);

    void deleteByRatingId(String ratingId);

    Optional<Rating> findByRatingId(String ratingId);

    void deleteByUserId(String userId);

    void deleteByCollegeId(String collegeId);
}
