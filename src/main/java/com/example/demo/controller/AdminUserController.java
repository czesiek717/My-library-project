package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user/admin")
public class AdminUserController
{
    private UserService userService;

    @Autowired
    public AdminUserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/listUsers")
    public String listUsers(Model model)
    {
        List<User> users = userService.getUsersFrom();
        model.addAttribute("users", users);
        return "admin/listUsers";
    }

    @GetMapping("/createUser")
    public String createUser(Model model)
    {
        model.addAttribute("user", new User());
        return "admin/createUser";
    }

    @PostMapping("/createUser")
    public String createUserProcess(@Valid @ModelAttribute("user") User user, BindingResult bindingResult)
    {
        System.out.println(bindingResult);
        if(bindingResult.hasErrors())
        {
            return "admin/createUser";
        }
        if(!userService.registerUser(user, user.getRole()))
        {
            return "redirect:/user/admin/createUser?error";
        }
        return "redirect:/user/admin/createUser?success";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") int id)
    {
        userService.deleteUser(id);
        return "redirect:/user/admin/listUsers";
    }
}
