package com.college.rating.UserService.entity;

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

    private College college;
}
