package com.example.demo.service;

import com.example.demo.entity.Bookshelf;

public interface BookshelfService
{
    void saveBookshelf(Bookshelf bookshelf);

    void deleteBookshelf(Bookshelf bookshelf);

    Bookshelf getBookshelf(int id);

    Bookshelf getBookshelfWithBooks(int id);
}
