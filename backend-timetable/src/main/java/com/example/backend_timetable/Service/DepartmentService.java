package com.example.backend_timetable.Service;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;
import com.example.backend_timetable.Repository.SessionRepository;
import com.example.backend_timetable.collection.Department;
import com.example.backend_timetable.collection.Session;



@Service
public class DepartmentService {

    @Autowired
    private SessionRepository sessionRepository;

     public ResponseEntity<String> addDepartmentToSession(String sessionId, Department department) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }
        Session session = sessionOptional.get();
        department.setDepartmentId(UUID.randomUUID().toString()); 
        department.setDepartmentName(department.getDepartmentName());
        session.getDepartment().add(department);
        sessionRepository.save(session);
        return new ResponseEntity<>("Department added successfully", HttpStatus.OK);
    }


    public ResponseEntity<?> getAllDepartments(String sessionId) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(sessionOptional.get().getDepartment(), HttpStatus.OK);
    }


    public ResponseEntity<?> getDepartmentById(String sessionId, String departmentId) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }

        Session session = sessionOptional.get();
        for (Department department : session.getDepartment()) {
            if (department.getDepartmentId().equals(departmentId)) {
                return new ResponseEntity<>(department, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Department not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> updateDepartmentInSession(String sessionId, String departmentId, Department updatedDepartment) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }

        Session session = sessionOptional.get();
        for (Department department : session.getDepartment()) {
            if (department.getDepartmentId().equals(departmentId)) {
                department.setDepartmentName(updatedDepartment.getDepartmentName());
                sessionRepository.save(session);
                return new ResponseEntity<>("Department updated successfully", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Department not found", HttpStatus.NOT_FOUND);
    }



    public ResponseEntity<String> deleteDepartmentFromSession(String sessionId, String departmentId) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }
        Session session = sessionOptional.get();
        boolean isRemoved = session.getDepartment().removeIf(department -> department.getDepartmentId().equals(departmentId));
        if (!isRemoved) {
            return new ResponseEntity<>("Department not found", HttpStatus.NOT_FOUND);
        }
        sessionRepository.save(session);
        return new ResponseEntity<>("Department deleted successfully", HttpStatus.OK);
    }
}