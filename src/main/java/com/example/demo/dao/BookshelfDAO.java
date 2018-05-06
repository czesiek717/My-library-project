package com.example.demo.dao;

import com.example.demo.entity.Bookshelf;

public interface BookshelfDAO
{
    void saveBookshelf(Bookshelf bookshelf);

    Bookshelf getBookshelf(int id);

    Bookshelf getBookshelfWithBooks(int id);

    void deleteBookshelf(Bookshelf bookshelf);
}
