package com.example.demo.controller;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.service.AuthorService;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/author")
public class AuthorController
{
    private AuthorService authorService;

    private BookService bookService;

    @Autowired
    public AuthorController(AuthorService authorService, BookService bookService)
    {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping("")
    public String authorPage(@RequestParam("id") int id, Model model)
    {
        Author author = authorService.getAuthorWithBooks(id);
        List<Book> books = new ArrayList<>();
        for(Book book : author.getBooks())
        {
          Book book1 = bookService.getBookWithEverything(book.getId());
          book1.setDescription(book1.getDescription().length() >= 200 ? book1.getDescription().substring(0, 200) + "..."
                  : book1.getDescription());
          books.add(book1);
        }
        model.addAttribute("author", author);
        model.addAttribute("books", books);
        return "author";
    }
}
