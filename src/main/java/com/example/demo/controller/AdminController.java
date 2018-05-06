package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/admin")
public class AdminController
{
    @RequestMapping("")
    public String adminPanel()
    {
        return "admin/adminPanel";
    }
}