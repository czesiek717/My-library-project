package com.example.demo.service;

import com.example.demo.entity.Review;

import java.util.List;

public interface ReviewService
{
    void saveReview(Review review);

    Review getReview(int id);

    List<Review> getAllReviews();

    void deleteReview(Review review);

    void deleteReview(int id);
}
