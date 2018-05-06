package com.example.demo.entity;

import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Indexed
@Table(name = "book")
public class Book
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "Please provide a title")
    @Size(min = 1, max = 70)
    @Column(name = "title", unique = true)
    @Field(index = Index.YES, analyze = Analyze.YES)
    private String title;

    @NotNull(message = "Please provide a description")
    @Size(min = 1, max = 1000)
    @Column(name = "description")
    @Field(index = Index.YES, analyze = Analyze.YES)
    private String description;

    @NotNull(message = "Please private an image link")
    @Size(min = 1, max = 128)
    @Column(name = "image_link")
    private String imageLink;

    @Column(name = "rating")
    private double rating;

    @Transient
    private int tempRating;

    @NotNull
    @Column(name = "number_of_ratings")
    private int numberOfRatings;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "release_date_id")
    private ReleaseDate releaseDate;

    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    private List<Bookshelf> bookshelves;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @ManyToMany (fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable (
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @IndexedEmbedded
    private List<Author> authors;

    @Transient
    private String tempAuthors;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "user_rated_book",
            joinColumns = @JoinColumn(name = "book_id_3"),
            inverseJoinColumns = @JoinColumn(name = "user_id_1")
    )
    private List<User> usersWhoRated;

    public Book ()
    {
        authors = new ArrayList<>();
        reviews = new ArrayList<>();
        usersWhoRated = new ArrayList<>();
    }

    public void addAuthor(Author author)
    {
        authors.add(author);
    }

    public void addUserWhoRated(User user)
    {
        usersWhoRated.add(user);
    }

    public List<String> parseTempAuthors()
    {
        List<String> authors = null;
        if(tempAuthors != null)
        {
            authors = Arrays.asList(tempAuthors.split("\\s*,\\s*"));
        }
        return authors;
    }

    @Override
    public String toString()
    {
        return "Book{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", rating=" + rating +
                ", tempRating=" + tempRating +
                ", numberOfRatings=" + numberOfRatings +
                ", releaseDate=" + releaseDate +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(o == null || getClass() != o.getClass())
        {
            return false;
        }
        Book book = (Book) o;
        return id == book.id &&
                Objects.equals(title, book.title);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, title, description, imageLink, rating, tempRating, numberOfRatings, releaseDate,
                bookshelves, reviews, authors, tempAuthors, usersWhoRated);
    }

    public int getId ()
    {
        return id;
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
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

    public double getRating ()
    {
        return rating;
    }

    public void setRating (double rating)
    {
        this.rating = rating;
    }

    public int getTempRating ()
    {
        return tempRating;
    }

    public void setTempRating (int tempRating)
    {
        this.tempRating = tempRating;
    }

    public int getNumberOfRatings ()
    {
        return numberOfRatings;
    }

    public void setNumberOfRatings (int numberOfRatings)
    {
        this.numberOfRatings = numberOfRatings;
    }

    public ReleaseDate getReleaseDate ()
    {
        return releaseDate;
    }

    public void setReleaseDate (ReleaseDate releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    public List<Bookshelf> getBookshelves ()
    {
        return bookshelves;
    }

    public void setBookshelves (List<Bookshelf> bookshelves)
    {
        this.bookshelves = bookshelves;
    }

    public List<Review> getReviews ()
    {
        return reviews;
    }

    public void setReviews (List<Review> reviews)
    {
        this.reviews = reviews;
    }

    public List<Author> getAuthors ()
    {
        return authors;
    }

    public void setAuthors (List<Author> authors)
    {
        this.authors = authors;
    }

    public String getTempAuthors ()
    {
        return tempAuthors;
    }

    public void setTempAuthors (String tempAuthors)
    {
        this.tempAuthors = tempAuthors;
    }

    public List<User> getUsersWhoRated ()
    {
        return usersWhoRated;
    }

    public void setUsersWhoRated (List<User> users)
    {
        this.usersWhoRated = users;
    }
}
