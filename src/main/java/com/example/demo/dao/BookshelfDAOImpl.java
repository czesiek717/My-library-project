package com.example.demo.dao;
import com.example.demo.entity.Bookshelf;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookshelfDAOImpl implements BookshelfDAO
{
    private SessionFactory sessionFactory;

    @Autowired
    public BookshelfDAOImpl(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveBookshelf (Bookshelf bookshelf)
    {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(bookshelf);
    }

    @Override
    public void deleteBookshelf (Bookshelf bookshelf)
    {
        Session session = sessionFactory.getCurrentSession();
        session.delete(bookshelf);
    }

    @Override
    public Bookshelf getBookshelf (int id)
    {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Bookshelf.class, id);
    }

    @Override
    public Bookshelf getBookshelfWithBooks (int id)
    {
        Session session = sessionFactory.getCurrentSession();
        Bookshelf bookshelf = session.get(Bookshelf.class, id);
        Hibernate.initialize(bookshelf.getBooks());
        return bookshelf;
    }
}
