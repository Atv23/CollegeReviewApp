package com.college.rating.UserService.service;

import com.college.rating.UserService.dao.UserRepository;
import com.college.rating.UserService.entity.College;
import com.college.rating.UserService.entity.Rating;
import com.college.rating.UserService.entity.User;
import com.college.rating.UserService.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class UserServiceimpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    // to call APIs of other services through HTTP method, old way
    @Autowired
    private RestTemplate restTemplate;
    // to call APIs of other services through HTTP method, new way
    @Autowired
    private RestClient restClient;

    private Logger logger = LoggerFactory.getLogger(UserServiceimpl.class);
    //to get all registered microservices
    @Autowired
    private DiscoveryClient discoveryClient;
    //to call RATINGSERVICE APIs from FeignClient
    @Autowired
    private RatingService ratingService;

    @Override
    public User createUser(User user) {
        //generates unique random ID
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        return this.userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = this.userRepository.findAll();
        // Add logging to ensure the user list is fetched correctly
        logger.info("Fetched users: {}", userList);
        //set ratings for all user
        for(User user: userList)
        {
            Rating[] ratingsArray = this.restClient.get()
                            .uri("http://localhost:8083/ratings/user/"+ user.getUserId())
                            .retrieve()
                            .body(Rating[].class);
            List<Rating> ratings = Arrays.stream(ratingsArray).toList();
            logger.info("{}", ratings);
            for(Rating rating : ratings)
            {
                //api call to COLLEGE_SERVICE
                College college = this.restClient.get()
                        .uri("http://localhost:8082/colleges/"+rating.getCollegeId())
                        .retrieve()
                        .body(College.class);
                logger.info("{}", college);
                //set the college to rating
                rating.setCollege(college);
            }
            user.setRating(ratings);
        }
        return userList;
    }

    @Override
    public User getUser(String userId) {
        User user = this.userRepository.findByUserId(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with given ID is not found on server : " + userId));

        //fetch the ratings of the user from RATING_SERVICE
        Rating[] userRatingArray = restTemplate.getForObject("http://ratingservice/ratings/user/" +
                user.getUserId(), Rating[].class);
        List<Rating> userRatings = Arrays.stream(userRatingArray).toList();
        //fetch the college whose rating is done from COLLEGE_SERVICE
        for(Rating rating : userRatings)
        {
            //api call to COLLEGE_SERVICE
            College college = restTemplate.getForObject("http://collegeservice/colleges/"
                    + rating.getCollegeId(), College.class);
            //set the college to rating
            rating.setCollege(college);
        }
        user.setRating(userRatings);
        return user;
    }


    @Override
    public User updateUser(User user, String id) {
        User oldUser = this.userRepository.findByUserId(id).get();
        oldUser.setUserEmail(user.getUserEmail());
        oldUser.setUserName(user.getUserName());
        oldUser.setUserGender(user.getUserGender());
        oldUser.setUserDescription(user.getUserDescription());
        return this.userRepository.save(oldUser);
    }

    @Override
    public void deleteUser(String userId) {
        //call delete api in RATINGSERVICE to delete all ratings done by user
        this.ratingService.deleteRatingsOfUser(userId); // Call the Feign client to delete ratings
        // Now, delete the user
        this.userRepository.deleteById(userId);
    }

    @Override
    public List<String> getRegisteredServices() {
        List<String> services = discoveryClient.getServices();
        services.forEach(service -> System.out.println("Registered service: " + service));
        return services;
    }

    @Override
    public boolean userAlreadyExists(String userEmail) {
        if(this.userRepository.existsByUserEmail(userEmail))
            return true;
        return false;
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.findByUserEmail(email);
    }
}
