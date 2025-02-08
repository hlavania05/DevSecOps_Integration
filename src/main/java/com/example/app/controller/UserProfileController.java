package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.model.User;
import com.example.app.repository.UserRepository;
import com.example.app.service.S3Service;

import java.util.Optional;

@Controller
@RequestMapping("/home")
public class UserProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private S3Service s3Service;

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("UserProfile", new User());
        return "form";
    }

    @PostMapping("/saveUser")
    public String saveUser(@RequestParam String username, RedirectAttributes redirectAttributes) {
        if (!userRepository.findByUsername(username).isPresent()) {
            User newUser = new User();
            newUser.setUsername(username);
            userRepository.save(newUser);
        }
        return "redirect:/home/profile?username=" + username;
    }

    @GetMapping("/profile")
    public String showProfile(@RequestParam String username, Model model) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            User userData = user.get();

            // ✅ Generate Pre-Signed URL
            String preSignedUrl = s3Service.getPreSignedUrl(userData.getProfileImage());
            System.out.println("Pre-Signed URL in Controller: " + preSignedUrl); // Debugging

            model.addAttribute("user", userData);
            model.addAttribute("profileImage", preSignedUrl);
            return "profile";
        }
        return "error";
    }
}
