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
    public ResponseEntity<Void> updateProfileImage(@PathVariable Long userId,
            @RequestParam("file") MultipartFile file) {
        try {
            // ✅ Validate File Type
            String contentType = file.getContentType();
            if (!("image/jpeg".equals(contentType) || "image/png".equals(contentType))) {
                return ResponseEntity.badRequest().build(); // ❌ Invalid file type
            }

            // ✅ Upload to S3
            String fileKey = s3Service.uploadFile(file);

            // ✅ Update User in Database
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setProfileImage(fileKey);
                userRepository.save(user);
                return ResponseEntity.ok().build(); // ✅ Success (No Message in Response)
            } else {
                return ResponseEntity.notFound().build(); // ❌ User Not Found
            }

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build(); // ❌ Error Uploading
        }
    }

    // S3 Bucket Create krna and then usme Cors Permission set krna

}
