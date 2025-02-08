package com.example.app.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.app.model.User;
import com.example.app.repository.UserRepository;
import com.example.app.service.S3Service;

@RestController
@RequestMapping("/api/profile")
public class UpdateProfile {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private S3Service s3Service;

    @PostMapping("/{userId}/updateProfileImage")
    public ResponseEntity<String> updateProfileImage(@PathVariable Long userId,
            @RequestParam("file") MultipartFile file) {
        try {
            // ✅ Validate File Type
            String contentType = file.getContentType();
            if (!("image/jpeg".equals(contentType) || "image/png".equals(contentType))) {
                return ResponseEntity.badRequest().body("Only JPEG or PNG files are allowed.");
            }

            // ✅ Upload to S3
            String fileKey = s3Service.uploadFile(file);

            // ✅ Update User in Database
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setProfileImage(fileKey); // Save only the S3 object key
                userRepository.save(user);
                return ResponseEntity.ok("Profile pic updated successfully!!");
            } else {
                return ResponseEntity.badRequest().body("User not found.");
            }

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error uploading image: " + e.getMessage());
        }
    }
    // S3 Bucket Create krna and then usme Cors Permission set krna

}
