package com.example.demo.dao;

import com.example.demo.entity.Book;
import com.example.demo.service.ReviewService;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDAOImpl implements BookDAO
{
    private SessionFactory sessionFactory;

    private ReviewService reviewService;

    @Autowired
    public BookDAOImpl(SessionFactory sessionFactory, ReviewService reviewService)
    {
        this.sessionFactory = sessionFactory;
        this.reviewService = reviewService;
    }

    @Override
    public boolean saveBook(Book book)
    {
        Session session = sessionFactory.getCurrentSession();
        try
        {
            session.saveOrUpdate(book);
        }
        catch(Exception ignored)
        {
            session.clear();
            return false;
        }
        return true;
    }

    @Override
    public Book getBook(int id)
    {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);
    }

    @Override
    public Book getBookWithReviews(int id)
    {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        Hibernate.initialize(book.getReviews());
        return book;
    }

    @Override
    public Book getBookWithAuthors(int id)
    {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        Hibernate.initialize(book.getAuthors());
        return book;
    }

    @Override
    public Book getBookWithBookShelves(int id)
    {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        Hibernate.initialize(book.getBookshelves());
        return book;
    }

    @Override
    public Book getBookWithEverything(int id)
    {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        Hibernate.initialize(book.getAuthors());
        Hibernate.initialize(book.getReviews());
        Hibernate.initialize(book.getUsersWhoRated());
        return book;
    }

    @Override
    public Book loadBooksObjects(Book book)
    {
        Session session = sessionFactory.getCurrentSession();
        Book book1 = session.get(Book.class, book.getId());
        Hibernate.initialize(book1.getAuthors());
        Hibernate.initialize(book1.getReviews());
        Hibernate.initialize(book1.getUsersWhoRated());
        return book1;
    }

    @Override
    public List<Book> getAllBooks()
    {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Book");
        return query.getResultList();
    }

    @Override
    public List<Book> getAllBooksWithAuthors()
    {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Book");
        List<Book> books = query.getResultList();
        books.forEach(b -> Hibernate.initialize(b.getAuthors()));
        return books;
    }

    @Override
    public List<Book> getAllBooksWithEverything()
    {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Book");
        List<Book> books = query.getResultList();
        initializeBookComponents(books);
        return books;
    }

    @Override
    public List<Book> getBooksFromWithEverything(int offset, int limit)
    {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Book");
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        List<Book> books = query.getResultList();
        initializeBookComponents(books);
        return books;
    }

    @Override
    public void deleteBook(Book book)
    {
        Session session = sessionFactory.getCurrentSession();
        removeBooksReviews(book, session);
        session.delete(book);
    }

    @Override
    public void deleteBook(int id)
    {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        removeBooksReviews(book, session);
        session.delete(book);
    }

    private void removeBooksReviews(Book book, Session session)
    {
        if(book.getReviews() != null && book.getReviews().size() > 0)
        {
            book.getReviews().forEach(review -> reviewService.deleteReview(review));
        }
        if(book.getBookshelves() != null && book.getBookshelves().size() > 0)
        {
            book.getBookshelves().forEach(bookshelf ->
            {
                bookshelf.removeBook(book);
                session.save(bookshelf);
            });
        }
    }

    private void initializeBookComponents(List<Book> books)
    {
        for(Book book : books)
        {
            Hibernate.initialize(book.getAuthors());
            Hibernate.initialize(book.getReviews());
            Hibernate.initialize(book.getUsersWhoRated());
        }
    }

    @Override
    public long countBooks()
    {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("select count(*) from Book");
        return (long) query.getSingleResult();
    }

    @Override
    public void indexBooks()
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
    public List<Book> searchForBook(String searchText)
    {
        try
        {
            Session session = sessionFactory.getCurrentSession();

            FullTextSession fullTextSession = Search.getFullTextSession(session);

            QueryBuilder queryBuilder = fullTextSession.getSearchFactory()
                    .buildQueryBuilder().forEntity(Book.class).get();
            org.apache.lucene.search.Query query = queryBuilder
                    .keyword().onFields("title", "description", "authors.firstName", "authors.lastName")
                    .matching(searchText).createQuery();
            Query hibQuery = fullTextSession.createFullTextQuery(query, Book.class);

            List<Book> books = hibQuery.getResultList();
            initializeBookComponents(books);
            return books;
        }
        catch(Exception e)
        {
            throw e;
        }
    }
}
