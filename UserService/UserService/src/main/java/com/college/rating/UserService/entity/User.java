package com.college.rating.UserService.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class User {
    @Id
    @Column(name = "ID")
    private String userId;
    @Column(name = "Name")
    private String userName;
    @Column(name = "Email", unique = true)
    private String userEmail;
    @Column(name = "Gender")
    private String userGender;
    @Column(name = "About")
    private String userDescription;

    @Transient  //to not store in DB
    private List<Rating> rating = new ArrayList<>();
}
