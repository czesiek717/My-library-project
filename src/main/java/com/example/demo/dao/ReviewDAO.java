package com.example.demo.dao;

import com.example.demo.entity.Review;

import java.util.List;

public interface ReviewDAO
{
    void saveReview(Review review);

    Review getReview(int id);

    List<Review> getAllReviews();

    void deleteReview(Review review);

    void deleteReview(int id);
}
