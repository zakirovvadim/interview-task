package com.mycompany.interviewtask.repository;

import com.mycompany.interviewtask.entities.Rating;
import com.mycompany.interviewtask.repository.interfaces.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingRepositoryImpl implements RatingRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<Rating> findAllRatings() {
        return jdbcTemplate.query("SELECT * FROM Rating",  BeanPropertyRowMapper.newInstance(Rating.class));
    }
}
