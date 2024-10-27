package com.college.rating.CollegeService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "Colleges")
public class College {
    @Id
    private String collegeId;
    private String collegeName;
    private String collegeType;
    private String collegeLocation;
    private String collegeEmail;
    private String collegePhoneNumber;
    private Integer collegeEstablishedYear;
    @Column(length = 1000) // To allow for longer descriptions
    private String collegeDescription;

    @Transient
    private List<Rating> ratingList = new ArrayList<>();

}
