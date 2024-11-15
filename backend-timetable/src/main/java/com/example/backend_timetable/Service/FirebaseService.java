package com.example.backend_timetable.Service;

import org.springframework.stereotype.Service;

import com.example.backend_timetable.DTO.AuthRequest;
import com.example.backend_timetable.DTO.AuthResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;

@Service
public class FirebaseService {

     public AuthResponse createUser(AuthRequest authRequest) {
        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(authRequest.getEmail())
                    .setPassword(authRequest.getPassword());

            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);

            String uid = userRecord.getUid();

    
            return new AuthResponse(false, null, uid);  
        } catch (Exception e) {
            return new AuthResponse(true, e.getMessage(), null);
        }
    }
}