package com.example.backend_timetable.Controller;

import com.example.backend_timetable.Service.StudentService;
import com.example.backend_timetable.collection.Session;
import com.example.backend_timetable.collection.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
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
    public ResponseEntity<String> updateStudent(
            @PathVariable String sessionId,
            @PathVariable String departmentId,
            @PathVariable String groupId,
            @PathVariable String studentId,
            @RequestBody Student updatedStudent) {
                return studentService.updateStudent(sessionId, departmentId, groupId, studentId, updatedStudent);
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
    public ResponseEntity<String> deleteStudent(
            @PathVariable String sessionId,
            @PathVariable String departmentId,
            @PathVariable String groupId,
            @PathVariable String studentId) {
        try {
            studentService.deleteStudent(sessionId, departmentId, groupId, studentId);
            return ResponseEntity.ok("Student with ID " + studentId + " has been successfully deleted.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Error: Student with ID " + studentId + " could not be found or deleted.");
        }
    }


    @PostMapping("/upload")
    public ResponseEntity<String> uploadStudents(
            @PathVariable String sessionId,
            @PathVariable String departmentId,
            @PathVariable String groupId,
            @RequestParam("file") MultipartFile file) {
        try {
            studentService.addStudentsFromExcel(file, sessionId, departmentId, groupId);
            return ResponseEntity.ok("Students added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
