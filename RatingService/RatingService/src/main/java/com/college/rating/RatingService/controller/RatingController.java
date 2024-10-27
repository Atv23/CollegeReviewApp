package com.college.rating.RatingService.controller;

import com.college.rating.RatingService.entity.Rating;
import com.college.rating.RatingService.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    //create
    @PostMapping
    public ResponseEntity<Rating> create(@RequestBody Rating rating)
    {
        Rating newRating = this.ratingService.createRating(rating);
        return new ResponseEntity<>(newRating, HttpStatus.CREATED);
    }
    //get single rating
    @GetMapping("/{ratingId}")
    public ResponseEntity<Rating> getRating(@PathVariable String ratingId)
    {
        return new ResponseEntity<>(this.ratingService.getRating(ratingId), HttpStatus.OK);
    }
    //get all ratings
    @GetMapping
    public ResponseEntity<List<Rating>> getAll()
    {
        return new ResponseEntity<>(this.ratingService.getAll(),HttpStatus.OK);
    }
    //get all ratings by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Rating>> getAllByUserId(@PathVariable String userId)
    {
        return new ResponseEntity<>(this.ratingService.getAllByUser(userId), HttpStatus.OK);
    }
    //get all ratings by college
    @GetMapping("/college/{collegeId}")
    public ResponseEntity<List<Rating>> getAllByCollegeId(@PathVariable String collegeId)
    {
        return new ResponseEntity<>(this.ratingService.getAllByCollege(collegeId), HttpStatus.OK);
    }
    //delete rating
    @DeleteMapping("/{ratingId}")
    public ResponseEntity<Void> deleteRating(@PathVariable String ratingId)
    {
        this.ratingService.deleteRating(ratingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //update ratings
    @PutMapping("/{ratingId}")
    public ResponseEntity<Rating> updateRating (@PathVariable String ratingId, @RequestBody Rating rating)
    {
        return new ResponseEntity<>(this.ratingService.updateRating(rating,ratingId),HttpStatus.OK);
    }
    //delete all ratings of a user
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteAllUserRatings(@PathVariable String userId)
    {
        this.ratingService.deleteAllUserRatings(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //delete all ratings of a college
    @DeleteMapping("/college/{collegeId}")
    public ResponseEntity<Void> deleteAllCollegeRatings(@PathVariable String collegeId)
    {
        this.ratingService.deleteAllCollegeRatings(collegeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
