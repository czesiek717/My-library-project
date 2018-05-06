package com.example.demo.controller;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.service.AuthorService;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user/admin")
public class AdminBookController
{
    private BookService bookService;

    private AuthorService authorService;

    private List<Author> authors;

    @Autowired
    public AdminBookController(BookService bookService, AuthorService authorService)
    {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/listBooks")
    public String listBooks(Model model)
    {
        List<Book> books = bookService.getAllBooksWithEverything();
        model.addAttribute("books", books);
        return "admin/listBooks";
    }

    @GetMapping("/createBook")
    public String createBook(Model model)
    {
        authors = new ArrayList<>();
        model.addAttribute("book", new Book());
        return "admin/createBook";
    }

    @PostMapping("/createBook")
    public String createBookProcess(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult)
    {
        System.out.println(bindingResult);
        if(bindingResult.hasErrors())
        {
            return "admin/createBook";
        }
        book.setAuthors(authors);
        authors = null;

        if(!updateAuthors(book) || !bookService.saveBook(book))
        {
            return "redirect:/user/admin/createBook?error";
        }
        return "redirect:/user/admin/createBook?success";
    }

    @GetMapping("/updateBook")
    public String updateBook(@RequestParam("id") int id, Model model)
    {
        Book book = bookService.getBookWithEverything(id);
        authors = book.getAuthors();
        model.addAttribute("book", book);
        return "admin/createBook";
    }

    @GetMapping("/deleteBook")
    public String deleteBook(@RequestParam("id") int id)
    {
        bookService.deleteBook(id);
        return "redirect:/user/admin/listBooks";
    }

    @GetMapping("/resetBooksRating")
    public String resetBooksRating(@RequestParam("id") int id)
    {
        Book book = bookService.getBookWithEverything(id);
        book.setRating(0.0);
        book.setNumberOfRatings(0);
        book.setUsersWhoRated(new ArrayList<>());
        bookService.saveBook(book);
        return "redirect:/user/admin/listBooks";
    }

    @GetMapping("/resetBooksAuthors")
    public String resetBooksAuthors(@RequestParam("id") int id)
    {
        Book book = bookService.getBookWithEverything(id);
        book.setAuthors(new ArrayList<>());
        bookService.saveBook(book);
        return "redirect:/user/admin/listBooks";
    }

    private boolean updateAuthors(Book book)
    {
        for(String s : book.parseTempAuthors())
        {
            if(s.split(" ").length < 2 || s.split(" ").length > 2)
            {
                if(book.getAuthors() == null || book.getAuthors().size() == 0)
                {
                    return false;
                }
            }
            else
            {
                String first = s.split("\\s")[0];
                String last = s.split("\\s")[1];
                Author author = authorService.getAuthor(first, last);
                if(author == null)
                {
                    return false;
                }
                else
                {
                    if(book.getAuthors() != null)
                    {
                        if(!book.getAuthors().contains(author))
                        {
                            book.addAuthor(author);
                        }
                    }
                    else
                    {
                        book.addAuthor(author);
                    }
                }
            }
        }
        return true;
    }
}