package com.example.demo.controller;

import com.example.demo.entity.Review;
import com.example.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/user/admin")
public class AdminReviewController
{
    private ReviewService reviewService;

    @Autowired
    public AdminReviewController(ReviewService reviewService)
    {
        this.reviewService = reviewService;
    }

    @GetMapping("/listReviews")
    public String listReviews(Model model)
    {
        List<Review> reviews = reviewService.getAllReviews();
        model.addAttribute("reviews", reviews);
        return "admin/listReviews";
    }

    @GetMapping("/deleteReview")
    public String deleteReview(@RequestParam("id") int id)
    {
        reviewService.deleteReview(id);
        return "redirect:/user/admin/listReviews";
    }
}
