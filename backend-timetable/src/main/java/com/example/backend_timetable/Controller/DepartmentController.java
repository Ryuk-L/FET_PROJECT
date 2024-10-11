package com.example.backend_timetable.Controller;

import com.example.backend_timetable.Service.DepartmentService;
import com.example.backend_timetable.collection.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/session")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // Add Department to Session
    @PostMapping("/{sessionId}/department")
    public ResponseEntity<String> addDepartmentToSession(@PathVariable String sessionId, @RequestBody Department department) {
        return departmentService.addDepartmentToSession(sessionId, department);
    }

    // Get All Departments in a Session
    @GetMapping("/{sessionId}/departments")
    public ResponseEntity<?> getAllDepartments(@PathVariable String sessionId) {
        return departmentService.getAllDepartments(sessionId);
    }

    // Get Department by ID in a Session
    @GetMapping("/{sessionId}/department/{departmentId}")
    public ResponseEntity<?> getDepartmentById(@PathVariable String sessionId, @PathVariable String departmentId) {
        return departmentService.getDepartmentById(sessionId, departmentId);
    }

    // Update Department in a Session
    @PutMapping("/{sessionId}/department/{departmentId}")
    public ResponseEntity<String> updateDepartmentInSession(@PathVariable String sessionId, @PathVariable String departmentId, @RequestBody Department department) {
        return departmentService.updateDepartmentInSession(sessionId, departmentId, department);
    }

    //delete Department in a Session
    @DeleteMapping("/{sessionId}/department/{departmentId}")
    public ResponseEntity<String> deleteDepartmentInSession(@PathVariable String sessionId, @PathVariable String departmentId)
    {
        return departmentService.deleteDepartmentFromSession(sessionId, departmentId);
    }

  
}
