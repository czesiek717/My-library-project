package com.example.demo.dao;

import com.example.demo.entity.Book;

import java.util.List;

public interface BookDAO
{
    boolean saveBook(Book book);

    Book getBook(int id);

    Book getBookWithReviews(int id);

    Book getBookWithAuthors(int id);

    Book getBookWithBookShelves(int id);

    Book getBookWithEverything(int id);

    Book loadBooksObjects(Book book);

    List<Book> getAllBooks();

    List<Book> getAllBooksWithAuthors();

    List<Book> getAllBooksWithEverything();

    List<Book> getBooksFromWithEverything(int offset, int limit);

    void deleteBook(Book book);

    void deleteBook(int id);

    long countBooks();

    void indexBooks();

    List<Book> searchForBook(String searchText);
}
