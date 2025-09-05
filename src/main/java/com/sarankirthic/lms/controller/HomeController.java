package com.sarankirthic.lms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home"; // Redirects to the /home page
    }



    @GetMapping("/home")
    public String home() {
        return "home"; // The name of your home page view (home.html or home.jsp)
    }
    @GetMapping("/about")
    public String about(){
        return "about";
    }
    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }
}


