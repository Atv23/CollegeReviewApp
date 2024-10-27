package com.college.rating.CollegeService.controllers;

import com.college.rating.CollegeService.entity.College;
import com.college.rating.CollegeService.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colleges")
public class CollegeController {

    @Autowired
    private CollegeService collegeService;

    //create
    @PostMapping
    public ResponseEntity<College> createCollege(@RequestBody College college)
    {
        College newCollege = this.collegeService.createCollege(college);
        return new ResponseEntity<>(newCollege, HttpStatus.CREATED);
    }
    //get 1 college
    @GetMapping("/{collegeId}")
    public ResponseEntity<College> getCollege(@PathVariable String collegeId)
    {
        College getCollege = this.collegeService.getCollege(collegeId);
        return new ResponseEntity<>(getCollege, HttpStatus.OK);
    }
    //create
    @GetMapping
    public ResponseEntity<List<College>> getAllColleges()
    {
        List<College> collegeList = this.collegeService.getAllColleges();
        return new ResponseEntity<>(collegeList, HttpStatus.OK);
    }
    //create
    @DeleteMapping("/{collegeId}")
    public ResponseEntity<Void> deleteCollege(@PathVariable String collegeId)
    {
        this.collegeService.deleteCollege(collegeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //create
    @PutMapping("/{collegeId}")
    public ResponseEntity<College> updateCollege(@PathVariable String collegeId,@RequestBody College college)
    {
        College updatedCollege = this.collegeService.updateCollege(collegeId,college);
        return new ResponseEntity<>(updatedCollege, HttpStatus.CREATED);
    }

}
