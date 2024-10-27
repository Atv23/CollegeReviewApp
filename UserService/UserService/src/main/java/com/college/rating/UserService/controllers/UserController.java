package com.college.rating.UserService.controllers;

import com.college.rating.UserService.entity.User;
import com.college.rating.UserService.service.UserService;
import com.college.rating.UserService.service.UserServiceimpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    //create user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user)
    {
        if(this.userService.userAlreadyExists(user.getUserEmail()))
        {
            System.out.println("user already exists with this email");
            return new ResponseEntity<>(user,HttpStatus.CONFLICT);
        }
        User createdUser = this.userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    int retryCount=1;
    //get 1 user
    @GetMapping("/{userId}")
//    @CircuitBreaker(name = "User_rating_college_breaker",fallbackMethod = "User_rating_college_fallback")
//    @Retry(name = "User_rating_college_retry",fallbackMethod = "User_rating_college_fallback" )
    @RateLimiter(name = "User_rating_college_rateLimiter" , fallbackMethod = "User_rating_college_fallback")
    public ResponseEntity<User> getUser(@PathVariable("userId") String id)
    {
        logger.info("Retry count: {}",retryCount);
        retryCount++;
        User user = this.userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    //creating fallback method for circuit breaker
    public ResponseEntity<User> User_rating_college_fallback(String userId, Exception e)
    {
        logger.info("Fallback excecuted because Rating/College Service is down: " + e.getMessage());
        User user = User.builder()
                        .userEmail("dummy@gmail.com")
                        .userGender("NA")
                        .userName("dummy")
                        .userDescription("NA")
                        .userId("dummyid")
                        .build();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    //get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers()
    {
        List<User> userList = this.userService.getAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    //delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId)
    {
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //update user
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody User updatedUser)
    {
        User user = this.userService.updateUser(updatedUser,userId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    @GetMapping("/get-services")
    public ResponseEntity<List<String>> getServices() {
        List<String> servicesList = this.userService.getRegisteredServices();
        return new ResponseEntity<>(servicesList,HttpStatus.OK);
    }

    @Value("${user.role}")
    private String userRole;

    @Value("${welcome.message}")
    private String welcomeMessage;

    @GetMapping("/config")
    public String getConfig() {
        return String.format("Role: %s, Message: %s", userRole, welcomeMessage);
    }
    //check if user exists by mail
    @PostMapping("/exist/{email}")
    public ResponseEntity<?> checkUserExists(@PathVariable String email) {
        boolean ifUserExists = this.userService.userAlreadyExists(email);
        if (ifUserExists) {
            User user = this.userService.getUserByEmail(email);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "User not found, please Sign Up!!");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }
}
