package com.example.demo.dao;

import com.example.demo.entity.Author;

import java.util.List;

public interface AuthorDAO
{
    void saveAuthor(Author author);

    Author getAuthor(int id);

    Author getAuthor(String first, String last);

    Author getAuthorWithBooks(int id);

    List<Author> getAllAuthors();

    List<Author> getAllAuthorsWithBooks();

    void deleteAuthor(Author author);

    void deleteAuthor(int id);

    void indexAuthors();

    List<Author> searchForAuthor(String searchText);
}
