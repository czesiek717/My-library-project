package com.example.demo.dao;

import com.example.demo.entity.Author;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class AuthorDAOImpl implements AuthorDAO
{
    private SessionFactory sessionFactory;

    @Autowired
    public AuthorDAOImpl(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveAuthor (Author author)
    {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(author);
    }

    @Override
    public Author getAuthor (int id)
    {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Author.class, id);
    }

    @Override
    public Author getAuthor (String firstName, String lastName)
    {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Author a where a.firstName = :firstName and a.lastName = :lastName");
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        Author author;
        try
        {
            author = (Author)query.getSingleResult();
        }
        catch(NoResultException e)
        {
            return null;
        }
        return author;
    }

    @Override
    public Author getAuthorWithBooks (int id)
    {
        Session session = sessionFactory.getCurrentSession();
        Author author = session.get(Author.class, id);
        Hibernate.initialize(author.getBooks());
        return author;
    }

    @Override
    public List<Author> getAllAuthors ()
    {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Author");
        return query.getResultList();
    }

    @Override
    public List<Author> getAllAuthorsWithBooks ()
    {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Author");
        List<Author> authors = query.getResultList();
        authors.forEach(a -> Hibernate.initialize(a.getBooks()));
        return authors;
    }

    @Override
    public void deleteAuthor (Author author)
    {
        Session session = sessionFactory.getCurrentSession();
        session.delete(author);
    }

    @Override
    public void deleteAuthor (int id)
    {
        Session session = sessionFactory.getCurrentSession();
        Author author = session.get(Author.class, id);
        session.delete(author);
    }

    @Override
    public void indexAuthors ()
    {
        try
        {
            Session session = sessionFactory.getCurrentSession();

            FullTextSession fullTextSession = Search.getFullTextSession(session);
            fullTextSession.createIndexer().startAndWait();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public List<Author> searchForAuthor (String searchText)
    {
        try
        {
            Session session = sessionFactory.getCurrentSession();

            FullTextSession fullTextSession = Search.getFullTextSession(session);

            QueryBuilder queryBuilder = fullTextSession.getSearchFactory()
                    .buildQueryBuilder().forEntity(Author.class).get();
            org.apache.lucene.search.Query query = queryBuilder
                    .keyword().onFields("firstName", "lastName", "description")
                    .matching(searchText).createQuery();
            Query hibQuery = fullTextSession.createFullTextQuery(query, Author.class);

            List<Author> authors = hibQuery.getResultList();
            authors.forEach(a-> Hibernate.initialize(a.getBooks()));
            return authors;
        }
        catch(Exception e)
        {
            throw e;
        }
    }
}
