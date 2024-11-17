package com.example.backend_timetable.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.backend_timetable.Service.ProgramService;
import com.example.backend_timetable.collection.Program;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin/session")
public class ProgramController {

    @Autowired
    private ProgramService programService;

    // Add or update a program for a group
    @PostMapping("{sessionId}/department/{departmentId}/group/{groupId}")
    public ResponseEntity<String> addOrUpdateProgramWithSubjects(
            @PathVariable String sessionId,
            @PathVariable String departmentId,
            @PathVariable String groupId,
            @RequestBody Program programData) {
        return programService.addOrUpdateProgramWithSubjects(sessionId, departmentId, groupId, programData);
    }

    // Delete a program from a group
    @DeleteMapping("{sessionId}/department/{departmentId}/group/{groupId}")
    public ResponseEntity<String> deleteProgramFromGroup(
            @PathVariable String sessionId,
            @PathVariable String departmentId,
            @PathVariable String groupId) {
        return programService.deleteProgramFromGroup(sessionId, departmentId, groupId);
    }

    // Get a program from a group
    @GetMapping("{sessionId}/department/{departmentId}/group/{groupId}")
    public ResponseEntity<Program> getProgramFromGroup(
            @PathVariable String sessionId,
            @PathVariable String departmentId,
            @PathVariable String groupId) {
        return programService.getProgramFromGroup(sessionId, departmentId, groupId);
    }
}
