package com.example.backend_timetable.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.backend_timetable.collection.Program;
import com.example.backend_timetable.Service.ProgramService;

@RestController
@RequestMapping("/admin/session")
public class ProgramController {

    @Autowired
    private  ProgramService programService;

    // Add a program to a group in a department within a session
    @PostMapping("/{sessionId}/department/{departmentId}/group/{groupId}")
    public ResponseEntity<String> addProgramToGroup(
            @PathVariable String sessionId,
            @PathVariable String departmentId,
            @PathVariable String groupId,
            @RequestBody Program programData) {
        
        // Call the service method to add the program
        return programService.addProgramWithSubjectsToGroup(sessionId, departmentId, groupId, programData);
    }
    
}
