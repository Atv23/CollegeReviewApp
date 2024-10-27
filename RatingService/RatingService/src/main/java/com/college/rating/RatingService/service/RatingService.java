package com.college.rating.RatingService.service;

import com.college.rating.RatingService.entity.Rating;

import java.util.List;

public interface RatingService {

    //create
    Rating createRating(Rating rating);

    //get all ratings
    List<Rating> getAll();

    //get single rating
    public Rating getRating(String ratingId);

    //get all ratings done by a user
    public List<Rating> getAllByUser(String userId);

    //get all ratings of a college
    public List<Rating> getAllByCollege(String collegeId);

    //delete rating by rating id
    public void deleteRating(String ratingId);

    //update rating by rating id
    public Rating updateRating(Rating rating, String ratingId);

    //delete all ratings of user
    void deleteAllUserRatings(String userId);

    //delete all ratings of college
    void deleteAllCollegeRatings(String collegeId);
}
