package com.example.backend_timetable.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_timetable.DTO.AuthRequest;
import com.example.backend_timetable.Service.FirebaseService;
import com.google.firebase.auth.UserRecord;

@RestController
@RequestMapping("/api/auth")
public class FirebaseController {

    @Autowired
    private  FirebaseService firebaseService;

    

    // POST endpoint to create a user
    // @PostMapping("/register")
    // public ResponseEntity<String> createUser(@RequestBody AuthRequest userDTO) {
    //     try {
    //         // Create user using FirebaseService
    //         UserRecord userRecord = firebaseService.createUser(userDTO.getEmail(), userDTO.getPassword());
    //         return ResponseEntity.status(HttpStatus.CREATED)
    //                 .body("User created successfully with UID: " + userRecord.getUid());
    //     } catch (Exception e) {
    //         // Handle exceptions such as invalid email or password
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    //                 .body("Failed to create user: " + e.getMessage());
    //     }
    // }
    
}
