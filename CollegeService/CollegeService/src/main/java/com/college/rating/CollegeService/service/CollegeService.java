package com.college.rating.CollegeService.service;

import com.college.rating.CollegeService.dao.CollegeRepository;
import com.college.rating.CollegeService.entity.College;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface CollegeService {
    //create
    public College createCollege(College college);

    //get 1 college
    public College getCollege(String collegeId);

    //get all colleges
    public List<College> getAllColleges();

    //delete college
    public void deleteCollege(String collegeId);

    //update college
    public College updateCollege(String collegeId, College newCollege);
}
