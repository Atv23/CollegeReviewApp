package com.college.rating.CollegeService.service;

import com.college.rating.CollegeService.dao.CollegeRepository;
import com.college.rating.CollegeService.entity.College;
import com.college.rating.CollegeService.entity.Rating;
import com.college.rating.CollegeService.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CollegeServiceImpl implements CollegeService{

    @Autowired
    private CollegeRepository collegeRepository;

    @Autowired
    private RatingService ratingService;

    @Override
    public College createCollege(College college) {
        college.setCollegeId(UUID.randomUUID().toString());
        return this.collegeRepository.save(college);
    }

    @Override
    public College getCollege(String collegeId) {
        College college =  this.collegeRepository.findByCollegeId(collegeId).orElseThrow(()-> new ResourceNotFoundException
                ("College with given Id not found!!"));
        //fetch all ratings of the college
        List<Rating> ratingList = ratingService.getAllRatingsOfCollege(collegeId);
        college.setRatingList(ratingList);
        return college;
    }

    @Override
    public List<College> getAllColleges() {
        List<College> collegeList = this.collegeRepository.findAll();
        //fetch ratings of colleges
        for(College college : collegeList)
        {
            //call API to RATING_SERVICE
            List<Rating> ratingList = this.ratingService.getAllRatingsOfCollege(college.getCollegeId());
            college.setRatingList(ratingList);
        }
        return collegeList;
    }

    @Override
    public void deleteCollege(String collegeId) {
        // First, delete the college's ratings
        this.ratingService.deleteAllRatingsOfCollege(collegeId); // Call the Feign client to delete ratings
        // Now, delete the college
        this.collegeRepository.deleteById(collegeId);
    }

    @Override
    public College updateCollege(String collegeId, College newCollege) {

        College oldCollege = this.collegeRepository.findByCollegeId(collegeId).get();
        oldCollege.setCollegeName(newCollege.getCollegeName());
        oldCollege.setCollegeEmail(newCollege.getCollegeEmail());
        oldCollege.setCollegeLocation(newCollege.getCollegeLocation());
        oldCollege.setCollegePhoneNumber(newCollege.getCollegePhoneNumber());
        oldCollege.setCollegeType(newCollege.getCollegeType());
        oldCollege.setCollegeEstablishedYear(newCollege.getCollegeEstablishedYear());
        oldCollege.setCollegeDescription(newCollege.getCollegeDescription());
        return this.collegeRepository.save(oldCollege);
    }
}
