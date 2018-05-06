package com.example.demo.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Please provide a username")
    @Size(min = 6, max = 15,
            message = "username must be between 6 and 15 characters")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "Please provide a password")
    @Size(min = 6, max = 60)
    @Column(name = "password")
    private String password;

    @Transient
    private String passwordRepeated;

    @Transient
    private boolean passwordMatches;

    @NotEmpty(message = "Please provide an email")
    @Email(message = "Please provide a valid email")
    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bookshelf_id")
    private Bookshelf bookshelf;

    @OneToMany(mappedBy = "user",
                cascade = CascadeType.ALL)
    private List<Review> reviews;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "usersWhoRated")
    private List<Book> ratedBooks;

    @Override
    public String toString ()
    {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
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
        User user = (User) o;
        return id == user.id &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, username, password, passwordRepeated, passwordMatches, email, role, bookshelf, reviews,
                ratedBooks);
    }

    public User ()
    {
        reviews = new ArrayList<>();
    }

    public int getId ()
    {
        return id;
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public String getUsername ()
    {
        return username;
    }

    public void setUsername (String username)
    {
        this.username = username;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public String getPasswordRepeated ()
    {
        return passwordRepeated;
    }

    public void setPasswordRepeated (String passwordRepeated)
    {
        this.passwordRepeated = passwordRepeated;
        passwordMatches = password.equals(passwordRepeated);
    }

    public boolean isPasswordMatches ()
    {
        return passwordMatches;
    }

    public void setPasswordMatches (boolean passwordMatches)
    {
        this.passwordMatches = passwordMatches;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getRole ()
    {
        return role;
    }

    public void setRole (String role)
    {
        this.role = role;
    }

    public Bookshelf getBookshelf ()
    {
        return bookshelf;
    }

    public void setBookshelf (Bookshelf bookshelf)
    {
        this.bookshelf = bookshelf;
    }

    public List<Review> getReviews ()
    {
        return reviews;
    }

    public void setReviews (List<Review> reviews)
    {
        this.reviews = reviews;
    }

    public List<Book> getRatedBooks ()
    {
        return ratedBooks;
    }

    public void setRatedBooks (List<Book> ratedBooks)
    {
        this.ratedBooks = ratedBooks;
    }
}
