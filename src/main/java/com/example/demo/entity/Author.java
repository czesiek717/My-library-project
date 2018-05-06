package com.example.demo.entity;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Indexed
@Table(name = "author")
public class Author
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "Please provide a first name")
    @Size(min = 1, max = 45)
    @Column(name = "first_name")
    @Field (index = Index.YES, analyze = Analyze.YES)
    private String firstName;

    @NotNull(message = "Please provide a last name")
    @Size(min = 1, max = 45)
    @Column(name = "last_name")
    @Field(index = Index.YES, analyze = Analyze.YES)
    private String lastName;

    @NotNull(message = "Please provide a description")
    @Size(min = 1, max = 1000)
    @Column(name = "description")
    @Field(index = Index.YES, analyze = Analyze.YES)
    private String description;

    @NotNull(message = "Please provide a image link")
    @Size(min = 1, max = 128)
    @Column(name = "image_link")
    private String imageLink;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authors")
    private List<Book> books;

    public Author ()
    {
        books = new ArrayList<>();
    }

    public Author (String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString ()
    {
        return "Author{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public boolean equals (Object obj)
    {
        if(obj == null)
        {
            return false;
        }
        if(! Author.class.isAssignableFrom(obj.getClass()))
        {
            return false;
        }
        final Author author = (Author) obj;
        return this.firstName.equals(author.getFirstName()) && this.lastName.equals(author.getLastName());
    }

    public void addBook(Book book)
    {
        books.add(book);
    }

    public int getId ()
    {
        return id;
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public String getFirstName ()
    {
        return firstName;
    }

    public void setFirstName (String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName ()
    {
        return lastName;
    }

    public void setLastName (String lastName)
    {
        this.lastName = lastName;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getImageLink ()
    {
        return imageLink;
    }

    public void setImageLink (String imageLink)
    {
        this.imageLink = imageLink;
    }

    public List<Book> getBooks ()
    {
        return books;
    }

    public void setBooks (List<Book> books)
    {
        this.books = books;
    }
}
