package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.entity.Bookshelf;
import com.example.demo.entity.User;
import com.example.demo.service.BookService;
import com.example.demo.service.BookshelfService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController
{
    private UserService userService;

    private BookService bookService;

    private BookshelfService bookshelfService;

    @Autowired
    public UserController(UserService userService, BookService bookService, BookshelfService bookshelfService)
    {
        this.userService = userService;
        this.bookService = bookService;
        this.bookshelfService = bookshelfService;
    }

    @RequestMapping("/login")
    public String login()
    {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model)
    {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerProgress(@Valid @ModelAttribute("user") User user, BindingResult bindingResult)
    {
        System.out.println(bindingResult);
        if(bindingResult.hasErrors())
        {
            return "register";
        }
        if(!userService.registerUser(user, "USER"))
        {
            return "redirect:/user/register?error";
        }
        return "redirect:/user/register?success";
    }

    @GetMapping("/bookshelf")
    public String bookshelf(Model model, Principal principal)
    {
        User user;
        Bookshelf bookshelf = null;
        List<Book> books = new ArrayList<>();
        if(principal != null)
        {
            String username = principal.getName();
            user = userService.getUserWithEverything(username);
            bookshelf = bookshelfService.getBookshelfWithBooks(user.getBookshelf().getId());
            for(Book book : bookshelf.getBooks())
            {
                Book book1 = bookService.getBookWithAuthors(book.getId());
                books.add(book1);
            }
        }
        model.addAttribute("bookshelf", bookshelf);
        model.addAttribute("books", books);
        return "bookshelf";
    }
}
