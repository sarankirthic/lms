package com.sarankirthic.lms.controller;

import com.sarankirthic.lms.model.Role;
import com.sarankirthic.lms.model.User;
import com.sarankirthic.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        // Check if the role is valid
        try {
            Role role = Role.valueOf(user.getRole().toString());
            user.setRole(role);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Invalid role selected.");
            return "register";
        }

        // Save the user using the service layer (with password encryption handled in the service)
        userService.saveUser(user);

        // Redirect to the login page after successful registration
        return "redirect:/login";
    }
}
