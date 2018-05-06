package com.example.demo.dao;

import com.example.demo.entity.Review;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewDaoImpl implements ReviewDAO
{
    private SessionFactory sessionFactory;

    @Autowired
    public ReviewDaoImpl(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveReview (Review review)
    {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(review);
    }

    @Override
    public Review getReview (int id)
    {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Review.class, id);
    }

    @Override
    public List<Review> getAllReviews ()
    {
        Session session = sessionFactory.getCurrentSession();
        Query query1 = session.createQuery("from Review");
        return query1.getResultList();
    }

    @Override
    public void deleteReview (Review review)
    {
        Session session = sessionFactory.getCurrentSession();
        session.delete(review);
    }

    @Override
    public void deleteReview (int id)
    {
        Session session = sessionFactory.getCurrentSession();
        Review review = session.get(Review.class, id);
        session.delete(review);
    }
}
