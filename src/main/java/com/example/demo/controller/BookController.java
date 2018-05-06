package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.service.BookService;
import com.example.demo.service.BookshelfService;
import com.example.demo.service.ReviewService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController
{
    private BookService bookService;

    private ReviewService reviewService;

    private UserService userService;

    private BookshelfService bookshelfService;

    private List<Author> authors;
    private List<User> usersWhoRated;

    @Autowired
    public BookController(BookService bookService, ReviewService reviewService, UserService userService,
            BookshelfService bookshelfService)
    {
        this.bookService = bookService;
        this.reviewService = reviewService;
        this.userService = userService;
        this.bookshelfService = bookshelfService;
    }

    @GetMapping("")
    public String bookPage(@RequestParam("id") int id, Model model, Principal principal)
    {
        Book book = bookService.getBookWithEverything(id);
        authors = book.getAuthors();
        usersWhoRated = book.getUsersWhoRated();
        book.getReviews().sort(Comparator.comparingInt(Review::getId));

        Review review = new Review();
        User user;

        if(principal != null)
        {
            user = getLoggedInUser(principal);
            Bookshelf bookshelf = bookshelfService.getBookshelfWithBooks(user.getBookshelf().getId());
            model.addAttribute("bookshelf", bookshelf);
            model.addAttribute("user", user);
        }
        model.addAttribute("review", review);
        model.addAttribute("book", book);
        return "book";
    }

    @PostMapping("/review")
    public String reviewProcess(@ModelAttribute("review") Review review, @RequestParam("booksID") int id,
                                RedirectAttributes redirectAttributes,
                                Principal principal)
    {
        Book book = bookService.getBookWithEverything(id);
        User user = getLoggedInUser(principal);

        review.setBook(book);
        review.setUser(user);
        reviewService.saveReview(review);

        redirectAttributes.addAttribute("id", book.getId());
        return "redirect:/book?success=1";
    }

    @PostMapping("/rating")
    public String ratingProcess(@ModelAttribute("book") Book book, RedirectAttributes redirectAttributes,
            Principal principal)
    {
        User user = getLoggedInUser(principal);

        double rating = book.getRating() * book.getNumberOfRatings();
        rating += book.getTempRating();
        book.setNumberOfRatings(book.getNumberOfRatings() + 1);
        book.setRating(rating / book.getNumberOfRatings());
        book.setAuthors(authors);
        book.setUsersWhoRated(usersWhoRated);
        authors = null;
        usersWhoRated = null;

        book.addUserWhoRated(user);
        bookService.saveBook(book);

        redirectAttributes.addAttribute("id", book.getId());
        return "redirect:/book?success=2";
    }

    @PostMapping("/addToBookshelf")
    public String addToBookshelfProcess(@RequestParam("id") int id,
                                        RedirectAttributes redirectAttributes,
                                        Principal principal)
    {
        Book book = bookService.getBookWithEverything(id);
        User user = getLoggedInUser(principal);
        Bookshelf bookshelf = bookshelfService.getBookshelfWithBooks(user.getBookshelf().getId());
        bookshelf.addBook(book);
        bookshelfService.saveBookshelf(bookshelf);
        bookService.saveBook(book);

        redirectAttributes.addAttribute("id", book.getId());
        return "redirect:/book?success=3";
    }

    @PostMapping("/removeFromBookshelf")
    public String removeFromBookshelf(@RequestParam("id") int id,
                                      @RequestParam("inBookshelf") boolean inBookshelf,
                                      RedirectAttributes redirectAttributes,
                                      Principal principal)
    {
        Book book = bookService.getBook(id);
        User user = getLoggedInUser(principal);
        Bookshelf bookshelf = bookshelfService.getBookshelfWithBooks(user.getBookshelf().getId());
        bookshelf.removeBook(book);
        bookshelfService.saveBookshelf(bookshelf);
        bookService.saveBook(book);

        redirectAttributes.addAttribute("id", book.getId());
        if(inBookshelf)
        {
            return "redirect:/user/bookshelf?success";
        }
        else
        {
            return "redirect:/book?success=4";
        }
    }

    private User getLoggedInUser(Principal principal)
    {
        User user = null;
        if(principal != null)
        {
            String username = principal.getName();
            user = userService.getUserWithEverything(username);
        }
        return user;
    }
}
