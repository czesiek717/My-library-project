package com.example.demo.service;

import com.example.demo.dao.ReviewDAO;
import com.example.demo.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService
{
    @Autowired
    private ReviewDAO reviewDAO;

    @Override
    @Transactional
    public void saveReview (Review review)
    {
        reviewDAO.saveReview(review);
    }

    @Override
    @Transactional
    public Review getReview (int id)
    {
        return reviewDAO.getReview(id);
    }

    @Override
    @Transactional
    public List<Review> getAllReviews ()
    {
        return reviewDAO.getAllReviews();
    }

    @Override
    @Transactional
    public void deleteReview (Review review)
    {
        reviewDAO.deleteReview(review);
    }

    @Override
    @Transactional
    public void deleteReview (int id)
    {
        reviewDAO.deleteReview(id);
    }
}
