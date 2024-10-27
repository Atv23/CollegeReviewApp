package com.college.rating.RatingService.service;

import com.college.rating.RatingService.dao.RatingRepository;
import com.college.rating.RatingService.entity.Rating;
import com.college.rating.RatingService.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RatingServiceImpl implements RatingService{

    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Rating createRating(Rating rating) {
        rating.setRatingId(UUID.randomUUID().toString());
        return this.ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getAll() {
        return this.ratingRepository.findAll();
    }

    @Override
    public Rating getRating(String ratingId) {
        return this.ratingRepository.findByRatingId(ratingId)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found with id: " + ratingId));
    }

    @Override
    public List<Rating> getAllByUser(String userId) {
        return this.ratingRepository.findByUserId(userId);
    }

    @Override
    public List<Rating> getAllByCollege(String collegeId) {
        return this.ratingRepository.findByCollegeId(collegeId);
    }

    @Override
    public void deleteRating(String ratingId) {
        // Check if the rating exists
        Rating existingRating = this.ratingRepository.findByRatingId(ratingId)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found with id: " + ratingId));

        this.ratingRepository.deleteByRatingId(ratingId);
    }

    @Override
    public Rating updateRating(Rating rating, String ratingId) {
        // Create an update object
        Update update = new Update();
        update.set("rating", rating.getRating());
        update.set("feedback", rating.getFeedback());

        // Execute the update
        Query query = new Query(Criteria.where("ratingId").is(ratingId));
        mongoTemplate.updateFirst(query, update, Rating.class);

        // Optionally return the updated rating
        return ratingRepository.findByRatingId(ratingId)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found with id: " + ratingId));
    }

    @Override
    public void deleteAllUserRatings(String userId) {
        this.ratingRepository.deleteByUserId(userId);
    }

    @Override
    public void deleteAllCollegeRatings(String collegeId) {
        this.ratingRepository.deleteByCollegeId(collegeId);
    }
}
