package com.example.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "release_date")
public class ReleaseDate
{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private int id;

    @NotNull
    @Column(name = "day")
    private int day;

    @NotNull
    @Column(name = "month")
    private int month;

    @NotNull
    @Column(name = "year")
    private int year;

    @OneToOne (mappedBy = "releaseDate", cascade = CascadeType.ALL)
    private Book book;

    @Override
    public String toString ()
    {
        return day + "." + month + "." + year;
    }

    public int getId ()
    {
        return id;
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public int getDay ()
    {
        return day;
    }

    public void setDay (int day)
    {
        this.day = day;
    }

    public int getMonth ()
    {
        return month;
    }

    public void setMonth (int month)
    {
        this.month = month;
    }

    public int getYear ()
    {
        return year;
    }

    public void setYear (int year)
    {
        this.year = year;
    }

    public Book getBook ()
    {
        return book;
    }

    public void setBook (Book book)
    {
        this.book = book;
    }
}
