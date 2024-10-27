package com.college.rating.CollegeService.dao;

import com.college.rating.CollegeService.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollegeRepository extends JpaRepository<College,String> {

    public Optional<College> findByCollegeId(String collegeId);
}
