package com.example.demo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bookshelf")
public class Bookshelf
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(mappedBy = "bookshelf", cascade = CascadeType.ALL)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "book_bookshelf",
            joinColumns = @JoinColumn(name = "bookshelf_id_1"),
            inverseJoinColumns = @JoinColumn(name = "book_id_2")
    )
    private List<Book> books;

    public Bookshelf(User user)
    {
        this.user = user;
    }

    public Bookshelf(){}

    public void addBook(Book book)
    {
        if(books == null)
        {
            books = new ArrayList<>();
        }
        books.add(book);
    }

    public void removeBook(Book book)
    {
        books.remove(book);
    }

    @Override
    public String toString()
    {
        return "Bookshelf{" +
                "user=" + user +
                '}';
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public List<Book> getBooks()
    {
        return books;
    }

    public void setBooks(List<Book> books)
    {
        this.books = books;
    }
}
