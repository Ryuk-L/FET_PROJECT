package com.example.backend_timetable.Service;

import com.example.backend_timetable.Repository.SessionRepository;
import com.example.backend_timetable.Repository.StudentRepository;
import com.example.backend_timetable.collection.Department;
import com.example.backend_timetable.collection.Group;
import com.example.backend_timetable.collection.Session;
import com.example.backend_timetable.collection.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class StudentService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private StudentRepository studentRepository;

    public ResponseEntity<String> addStudentToGroup(String sessionId, String departmentId, String groupId, Student data) {
        try {
            Session session = findSessionById(sessionId);
            Department department = findDepartmentById(session, departmentId);
            Group group = findGroupById(department, groupId);
            System.out.println("\n\n\n"+studentRepository.existsByName(data.getName()+"\n\n"));

            if (studentRepository.existsByName(data.getName())) {
                return ResponseEntity.status(400).body("Student with this name already exists");
            }

            Student student = new Student();
            student.setEmail(data.getEmail());
            student.setName(data.getName());
            student.setId(UUID.randomUUID().toString());
            student.setGroup(data.getGroup());
            student.setCin(data.getCin());
            
            
            group.getStudents().add(student);
            sessionRepository.save(session);


            return new ResponseEntity<>("Student added to group successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public Session updateStudent(String sessionId, String departmentId, String groupId, String studentId, Student updatedStudent) {
        Session session = findSessionById(sessionId);
        Department department = findDepartmentById(session, departmentId);
        Group group = findGroupById(department, groupId);

        // Find and update the student
        group.getStudents().stream()
                .filter(student -> student.getId().equals(studentId))
                .findFirst()
                .ifPresent(student -> {
                    student.setName(studentId);updatedStudent.getName();
                    student.setEmail(updatedStudent.getEmail());
                    student.setCin(updatedStudent.getCin());
                    student.setGroup(updatedStudent.getGroup());
                });

        return sessionRepository.save(session);
    }

    public Student getStudent(String sessionId, String departmentId, String groupId, String studentId) {
        Session session = findSessionById(sessionId);
        Department department = findDepartmentById(session, departmentId);
        Group group = findGroupById(department, groupId);

        // Retrieve the student
        return group.getStudents().stream()
                .filter(student -> student.getId().equals(studentId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public Session deleteStudent(String sessionId, String departmentId, String groupId, String studentId) {
        Session session = findSessionById(sessionId);
        Department department = findDepartmentById(session, departmentId);
        Group group = findGroupById(department, groupId);

        // Remove the student
        group.setStudents(
                group.getStudents().stream()
                        .filter(student -> !student.getId().equals(studentId))
                        .toList()
        );

        return sessionRepository.save(session);
    }

    private Session findSessionById(String sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
    }

    private Department findDepartmentById(Session session, String departmentId) {
        return session.getDepartment().stream()
                .filter(department -> department.getDepartmentId().equals(departmentId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Department not found"));
    }

    private Group findGroupById(Department department, String groupId) {
        return department.getGroups().stream()
                .filter(group -> group.getGroupId().equals(groupId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Group not found"));
    }
}
