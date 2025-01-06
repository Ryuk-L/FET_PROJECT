package org.example.mety;

import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    public void createUserDatabase(String name) {
        // Simulate creating a user-specific database collection
        System.out.println("Creating user database with name: " + name);
    }

    public void createGeneralDatabase(String name) {
        // Simulate creating a general database collection
        System.out.println("Creating general database with name: " + name);
    }
}
