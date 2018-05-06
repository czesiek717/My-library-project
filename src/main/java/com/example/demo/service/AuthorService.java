package com.example.demo.service;

import com.example.demo.entity.Author;

import java.util.List;

/**
 * class used to communicate with database,
 * enables DAO class to create <tt>SessionFactory</tt>
 */
public interface AuthorService
{
    /**
     * enables
     * {@link com.grzesiek.project_1.dao.AuthorDAO#saveAuthor(Author)}
     * method to communicate with database
     * @param author author to save
     */
    void saveAuthor(Author author);

    /**
     * enables
     * {@link com.grzesiek.project_1.dao.AuthorDAO#getAuthor(int)}
     * method to communicate with database
     * @param id id of author to get
     * @return author of given id
     */
    Author getAuthor(int id);

    /**
     * enables
     * {@link com.grzesiek.project_1.dao.AuthorDAO#getAuthor(String, String)}
     * method to communicate with database
     * @param first first name of author to get
     * @param last last name of author to get
     * @return author of given credentials
     */
    Author getAuthor(String first, String last);

    /**
     * enables
     * {@link AuthorDAO#getAuthorWithBooks(int)}
     * method to communicate with database
     * @param id id of author to get
     * @return author of given id with
     *      books list initialized
     */
    Author getAuthorWithBooks(int id);

    /**
     * enables
     * {@link AuthorDAO#getAllAuthors()}
     * method to communicate with database
     * @return list of all authors from database
     */
    List<Author> getAllAuthors();

    /**
     * enables
     * {@link AuthorDAO#getAllAuthorsWithBooks()}
     * method to communicate with database
     * @return list of all authors with books
     *      list initialized from database
     */
    List<Author> getAllAuthorsWithBooks();

    /**
     * enables
     * {@link AuthorDAO#deleteAuthor(Author)}
     * method to communicate with database
     * @param author author to delete
     */
    void deleteAuthor(Author author);

    /**
     * enables
     * {@link AuthorDAO#deleteAuthor(int)}
     * method to communicate with database
     * @param id id of author to delete
     */
    void deleteAuthor(int id);

    /**
     * enables
     * {@link AuthorDAO#indexAuthors()}
     * method to communicate with database
     * <p>
     *     indexes author parameters for
     *     future search purposes
     * </p>
     */
    void indexAuthors();

    /**
     * enables
     * {@link AuthorDAO#searchForAuthor(String)}
     * method to communicate with database
     * <p>
     *     parses given string to find authors
     * </p>
     * @param searchText text to parse
     * @return list of authors received from search
     */
    List<Author> searchForAuthor(String searchText);
}
