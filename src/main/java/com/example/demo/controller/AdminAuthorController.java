package com.example.demo.controller;

import com.example.demo.entity.Author;
import com.example.demo.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user/admin")
public class AdminAuthorController
{
    private AuthorService authorService;

    @Autowired
    public AdminAuthorController(AuthorService authorService)
    {
        this.authorService = authorService;
    }

    @GetMapping("/listAuthors")
    public String listAuthors(Model model)
    {
        List<Author> authors = authorService.getAllAuthorsWithBooks();
        model.addAttribute("authors", authors);
        return "admin/listAuthors";
    }

    @GetMapping("/createAuthor")
    public String createAuthor(Model model)
    {
        model.addAttribute("author", new Author());
        return "admin/createAuthor";
    }

    @PostMapping("/createAuthor")
    public String createAuthorProcess(@Valid @ModelAttribute("author") Author author, BindingResult bindingResult)
    {
        System.out.println(bindingResult);
        if(bindingResult.hasErrors())
        {
            return "admin/createAuthor";
        }
        authorService.saveAuthor(author);
        return "redirect:/user/admin/createAuthor?success";
    }

    @GetMapping("/updateAuthor")
    public String updateAuthor(@RequestParam("id") int id, Model model)
    {
        Author author = authorService.getAuthor(id);
        model.addAttribute("author", author);
        return "admin/createAuthor";
    }

    @GetMapping("/deleteAuthor")
    public String deleteAuthor(@RequestParam("id") int id)
    {
        authorService.deleteAuthor(id);
        return "redirect:/user/admin/listAuthors";
    }
}