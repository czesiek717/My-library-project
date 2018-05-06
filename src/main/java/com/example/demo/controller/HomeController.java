package com.example.demo.controller;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.service.AuthorService;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController
{
    private BookService bookService;

    private AuthorService authorService;

    @Autowired
    public HomeController(BookService bookService, AuthorService authorService)
    {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/")
    public String homePage (Model model)
    {
        List<Book> books = bookService.getBooksFromWithEverything(0, 5);

        this.shortenBookDescriptions(books);
        long numberOfBooks = bookService.countBooks();
        int numberOfPages = (int)Math.ceil(numberOfBooks / 5.0);

        model.addAttribute("books", books);
        model.addAttribute("numberOfPages", numberOfPages);
        model.addAttribute("text", new String());
        return "index";
    }

    @GetMapping("/{pageIndex}")
    public String page(@PathVariable("pageIndex") int pageIndex,
                       Model model)
    {
        List<Book> books = bookService.getBooksFromWithEverything((pageIndex - 1) * 5, 5);

        this.shortenBookDescriptions(books);
        long numberOfBooks = bookService.countBooks();
        int numberOfPages = (int)Math.ceil(numberOfBooks / 5.0);

        model.addAttribute("books", books);
        model.addAttribute("numberOfPages", numberOfPages);
        model.addAttribute("text", new String());
        return "index";
    }

    @PostMapping("/search")
    public String searchPage(@RequestParam("text") String searchText, Model model)
    {
        List<Book> books = bookService.searchForBook(searchText);
        List<Author> authors = authorService.searchForAuthor(searchText);

        this.shortenBookDescriptions(books);
        this.shortenAuthorDescriptions(authors);

        model.addAttribute("books", books);
        model.addAttribute("authors", authors);
        model.addAttribute("text", new String());
        return "index";
    }

    @GetMapping("/403")
    public String accessDenied()
    {
        return "errors/403";
    }

    private void shortenBookDescriptions (List<Book> books)
    {
        books.forEach(b -> b.setDescription(b.getDescription().length() >= 200 ? b.getDescription().substring(0, 200) + "..." : b.getDescription()));
    }

    private void shortenAuthorDescriptions (List<Author> authors)
    {
        authors.forEach(a -> a.setDescription(a.getDescription().length() >= 200 ? a.getDescription().substring(0, 200) + "..." : a.getDescription()));
    }
}