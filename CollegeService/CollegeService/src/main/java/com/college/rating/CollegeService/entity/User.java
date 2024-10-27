package com.college.rating.CollegeService.entity;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private String userId;
    private String userName;
    private String userEmail;
    private String userGender;
    private String userDescription;
}
