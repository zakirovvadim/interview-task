package com.mycompany.interviewtask.repository.interfaces;

import com.mycompany.interviewtask.entities.Rating;

import java.util.List;

public interface RatingRepository {
    List<Rating> findAllRatings();
}
