package com.example.demo.service;

import com.example.demo.dao.BookDAO;
import com.example.demo.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements BookService
{
    @Autowired
    private BookDAO bookDAO;

    @Override
    @Transactional
    public boolean saveBook (Book book)
    {
        return bookDAO.saveBook(book);
    }

    @Override
    @Transactional
    public Book getBook (int id)
    {
        return bookDAO.getBook(id);
    }

    @Override
    @Transactional
    public Book getBookWithReviews (int id)
    {
        return bookDAO.getBookWithReviews(id);
    }

    @Override
    @Transactional
    public Book getBookWithAuthors (int id)
    {
        return bookDAO.getBookWithAuthors(id);
    }

    @Override
    public Book getBookWithBookshelves (int id)
    {
        return bookDAO.getBookWithBookShelves(id);
    }

    @Override
    @Transactional
    public Book getBookWithEverything (int id)
    {
        return bookDAO.getBookWithEverything(id);
    }

    @Override
    @Transactional
    public Book loadBooksObjects (Book book)
    {
        return bookDAO.loadBooksObjects(book);
    }

    @Override
    @Transactional
    public List<Book> getAllBooks ()
    {
        return bookDAO.getAllBooks();
    }

    @Override
    @Transactional
    public List<Book> getAllBooksWithAuthors ()
    {
        return bookDAO.getAllBooksWithAuthors();
    }

    @Override
    @Transactional
    public List<Book> getAllBooksWithEverything ()
    {
        return bookDAO.getAllBooksWithEverything();
    }

    @Override
    @Transactional
    public List<Book> getBooksFromWithEverything (int offset, int limit)
    {
        return bookDAO.getBooksFromWithEverything(offset, limit);
    }

    @Override
    @Transactional
    public void deleteBook (Book book)
    {
        bookDAO.deleteBook(book);
    }

    @Override
    @Transactional
    public void deleteBook (int id)
    {
        bookDAO.deleteBook(id);
    }

    @Override
    @Transactional
    public long countBooks ()
    {
        return bookDAO.countBooks();
    }

    @Override
    @Transactional
    public void indexBooks ()
    {
        bookDAO.indexBooks();
    }

    @Override
    @Transactional
    public List<Book> searchForBook (String searchText)
    {
        return bookDAO.searchForBook(searchText);
    }
}
