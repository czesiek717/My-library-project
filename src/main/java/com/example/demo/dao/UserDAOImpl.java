package com.example.demo.dao;

import com.example.demo.entity.Bookshelf;
import com.example.demo.entity.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO
{
    private SessionFactory sessionFactory;

    @Autowired
    public UserDAOImpl(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveUser (User user)
    {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(user);
    }

    @Override
    public User getUser (int id)
    {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, id);
    }

    @Override
    public User getUser (String username)
    {
        Session session = sessionFactory.getCurrentSession();
        return (User)session.createQuery("select user1 from User user1 where user1.username = :username")
                .setParameter("username", username).uniqueResult();
    }

    @Override
    public User getUserWithBookshelf (int id)
    {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);
        Hibernate.initialize(user.getBookshelf());
        return user;
    }

    @Override
    public User getUserWithReviews (int id)
    {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);
        Hibernate.initialize(user.getReviews());
        return user;
    }

    @Override
    public User getUserWithEverything (int id)
    {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);
        Hibernate.initialize(user.getBookshelf());
        Hibernate.initialize(user.getReviews());
        return user;
    }

    @Override
    public User getUserWithEverything (String username)
    {
        Session session = sessionFactory.getCurrentSession();
        User user = (User)session.createQuery("select user1 from User user1 where user1.username = :username").setParameter("username", username).uniqueResult();
        Hibernate.initialize(user.getBookshelf());
        Hibernate.initialize(user.getReviews());
        Hibernate.initialize(user.getRatedBooks());
        return user;
    }

    @Override
    public boolean registerUser (User user, String role)
    {
        if(user.isPasswordMatches())
        {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("from User u where u.username = :username or u.email = :email");
            query.setParameter("username", user.getUsername());
            query.setParameter("email", user.getEmail());
            User user1 = null;
            try
            {
                user1 = (User) query.getSingleResult();
            }
            catch (NoResultException e)
            {

            }
            if(user1 != null)
            {
                return false;
            }
            Bookshelf bookshelf = new Bookshelf();
            bookshelf.setUser(user);

            user.setBookshelf(bookshelf);
            user.setRole(role);

            session.saveOrUpdate(user);
            return true;
        }
        return false;
    }

    @Override
    public List<User> getAllUsers ()
    {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User");
        return query.getResultList();
    }

    @Override
    public void deleteUser (User user)
    {
        Session session = sessionFactory.getCurrentSession();
        session.delete(user);
    }

    @Override
    public void deleteUser (int id)
    {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);
        session.delete(user);
    }
}
