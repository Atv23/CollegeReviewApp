package com.college.rating.CollegeService.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="USERSERVICE")
public interface UserService {

//    @GetMapping("/")
//    User getUser()
}
