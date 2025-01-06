package org.example.mety;

import com.example.demo.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;

    @GetMapping("/createdb")
    public ResponseEntity<String> createDatabase(@RequestParam String type, @RequestParam String name) {
        try {
            if ("user".equals(type)) {
                databaseService.createUserDatabase(name);
            } else {
                databaseService.createGeneralDatabase(name);
            }
            return ResponseEntity.ok("Database created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating database: " + e.getMessage());
        }
    }
}
