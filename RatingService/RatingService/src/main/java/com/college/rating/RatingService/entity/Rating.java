package com.college.rating.RatingService.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Document("ratings")
public class Rating {
    @Id
    @Field("_id")
    private String ratingId;
    private String userId;
    private String collegeId;
    private int rating;
    private String feedback;
}
