package com.example.backend_timetable.Controller;

import com.example.backend_timetable.Service.StudentService;
import com.example.backend_timetable.collection.Session;
import com.example.backend_timetable.collection.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/session/{sessionId}/departments/{departmentId}/groups/{groupId}/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Add student to a group
    @PostMapping
    public ResponseEntity<String> addStudentToGroup(
            @PathVariable String sessionId,
            @PathVariable String departmentId,
            @PathVariable String groupId,
            @RequestBody Student student) {
        return studentService.addStudentToGroup(sessionId, departmentId, groupId, student);
    }

    // Update student details
    @PutMapping("/{studentId}")
    public ResponseEntity<Session> updateStudent(
            @PathVariable String sessionId,
            @PathVariable String departmentId,
            @PathVariable String groupId,
            @PathVariable String studentId,
            @RequestBody Student updatedStudent) {
        return ResponseEntity.ok(studentService.updateStudent(sessionId, departmentId, groupId, studentId, updatedStudent));
    }

    // Get student details
    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudent(
            @PathVariable String sessionId,
            @PathVariable String departmentId,
            @PathVariable String groupId,
            @PathVariable String studentId) {
        try {
            Student student = studentService.getStudent(sessionId, departmentId, groupId, studentId);
            return ResponseEntity.ok(student);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // Delete student from a group
    @DeleteMapping("/{studentId}")
    public ResponseEntity<Session> deleteStudent(
            @PathVariable String sessionId,
            @PathVariable String departmentId,
            @PathVariable String groupId,
            @PathVariable String studentId) {
        try {
            Session session = studentService.deleteStudent(sessionId, departmentId, groupId, studentId);
            return ResponseEntity.ok(session);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }
}
