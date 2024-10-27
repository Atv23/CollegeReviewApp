package com.college.rating.UserService.entity;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class College {
    private String collegeId;
    private String collegeName;
    private String collegeType;
    private String collegeLocation;
    private String collegeEmail;
    private String collegePhoneNumber;
    private Integer collegeEstablishedYear;
    private String collegeDescription;
}
