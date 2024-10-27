package com.college.rating.CollegeService.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Rating {

    private String ratingId;
    private String userId;
    private String collegeId;
    private int rating;
    private String feedback;

//    private User user;
}