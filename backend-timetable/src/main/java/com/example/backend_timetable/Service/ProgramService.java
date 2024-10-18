package com.example.backend_timetable.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.backend_timetable.collection.Program;
import com.example.backend_timetable.Repository.SessionRepository;
import com.example.backend_timetable.collection.Department;
import com.example.backend_timetable.collection.Group;
import com.example.backend_timetable.collection.Session;

@Service
public class ProgramService {

    @Autowired
    private SessionRepository sessionRepository;

    public ResponseEntity<String> addProgramWithSubjectsToGroup(String sessionId, String departmentId, String groupId, Program programData) {
        // Find the session by sessionId
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }
        Session session = sessionOptional.get();

        // Find the department by departmentId within the session
        Optional<Department> departmentOptional = session.getDepartment().stream()
            .filter(dept -> dept.getDepartmentId().equals(departmentId))
            .findFirst();
        if (!departmentOptional.isPresent()) {
            return new ResponseEntity<>("Department not found", HttpStatus.NOT_FOUND);
        }
        Department department = departmentOptional.get();

        // Find the group by groupId within the department
        Optional<Group> groupOptional = department.getGroups().stream()
            .filter(group -> group.getGroupId().equals(groupId))
            .findFirst();
        if (!groupOptional.isPresent()) {
            return new ResponseEntity<>("Group not found", HttpStatus.NOT_FOUND);
        }
        Group group = groupOptional.get();

        // Add the program with subjects and recurrence to the group
        group.setProgram(programData);

        // Save the updated session
        sessionRepository.save(session);

        return new ResponseEntity<>("Program with subjects added to group successfully", HttpStatus.OK);
    }

    
}
