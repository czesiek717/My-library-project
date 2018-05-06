package com.example.demo.service;
import com.example.demo.dao.AuthorDAO;
import com.example.demo.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService
{
    @Autowired
    AuthorDAO authorDAO;

    @Override
    @Transactional
    public void saveAuthor (Author author)
    {
        authorDAO.saveAuthor(author);
    }

    @Override
    @Transactional
    public Author getAuthor (int id)
    {
        return authorDAO.getAuthor(id);
    }

    @Override
    @Transactional
    public Author getAuthor (String first, String last)
    {
        return authorDAO.getAuthor(first, last);
    }

    @Override
    @Transactional
    public Author getAuthorWithBooks (int id)
    {
        return authorDAO.getAuthorWithBooks(id);
    }

    @Override
    @Transactional
    public List<Author> getAllAuthorsWithBooks ()
    {
        return authorDAO.getAllAuthorsWithBooks();
    }

    @Override
    @Transactional
    public List<Author> getAllAuthors ()
    {
        return authorDAO.getAllAuthors();
    }

    @Override
    @Transactional
    public void deleteAuthor (Author author)
    {
        authorDAO.deleteAuthor(author);
    }

    @Override
    @Transactional
    public void deleteAuthor (int id)
    {
        authorDAO.deleteAuthor(id);
    }

    @Override
    @Transactional
    public void indexAuthors ()
    {
        authorDAO.indexAuthors();
    }

    @Override
    @Transactional
    public List<Author> searchForAuthor (String searchText)
    {
        return authorDAO.searchForAuthor(searchText);
    }
}
