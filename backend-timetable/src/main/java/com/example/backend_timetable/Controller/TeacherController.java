package com.example.backend_timetable.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend_timetable.Service.TeacherService;
import com.example.backend_timetable.collection.Teacher;

@RestController
@RequestMapping("/admin/session")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/{sessionId}/teachers")
    public ResponseEntity<String> addTeacherToSession(
            @PathVariable String sessionId, 
            @RequestBody Teacher teacher) {
        return teacherService.addTeacherToSession(sessionId, teacher);
    }

    // Get all teachers in a session
    @GetMapping("/{sessionId}/teachers")
    public ResponseEntity<List<Teacher>> getAllTeachersInSession(@PathVariable String sessionId) {
        return teacherService.getAllTeachersInSession(sessionId);
    }

    // Get a teacher by ID in a session
    @GetMapping("/{sessionId}/teacher/{teacherId}")
    public ResponseEntity<Teacher> getTeacherByIdInSession(@PathVariable String sessionId, @PathVariable String teacherId) {
        return teacherService.getTeacherByIdInSession(sessionId, teacherId);
    }

    // Update a teacher in a session
    @PutMapping("/{sessionId}/teacher/{teacherId}")
    public ResponseEntity<String> updateTeacherInSession(@PathVariable String sessionId, @PathVariable String teacherId, @RequestBody Teacher updatedTeacher) {
        return teacherService.updateTeacherInSession(sessionId, teacherId, updatedTeacher);
    }

    // Delete a teacher from a session
    @DeleteMapping("/{sessionId}/teacher/{teacherId}")
    public ResponseEntity<String> deleteTeacherFromSession(@PathVariable String sessionId, @PathVariable String teacherId) {
        return teacherService.deleteTeacherFromSession(sessionId, teacherId);
    }


    // add Teachers from Excel
    @PostMapping("/{sessionId}/teachers/upload")
    public ResponseEntity<String> addTeachersFromExcel(@PathVariable String sessionId, @RequestParam("file") MultipartFile file) {
        return teacherService.addTeachersFromExcel(sessionId, file);
    }
}
