package com.example.demo.service;

import com.example.demo.dao.BookshelfDAO;
import com.example.demo.entity.Bookshelf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookshelfServiceImpl implements BookshelfService
{
    @Autowired
    BookshelfDAO bookshelfDAO;

    @Override
    @Transactional
    public void saveBookshelf (Bookshelf bookshelf)
    {
        bookshelfDAO.saveBookshelf(bookshelf);
    }

    @Override
    @Transactional
    public void deleteBookshelf (Bookshelf bookshelf)
    {
        bookshelfDAO.deleteBookshelf(bookshelf);
    }

    @Override
    @Transactional
    public Bookshelf getBookshelf (int id)
    {
        return bookshelfDAO.getBookshelf(id);
    }

    @Override
    @Transactional
    public Bookshelf getBookshelfWithBooks (int id)
    {
        return bookshelfDAO.getBookshelfWithBooks(id);
    }
}
