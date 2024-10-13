package com.example.backend_timetable.Service;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.example.backend_timetable.Repository.SessionRepository;
import com.example.backend_timetable.collection.Department;
import com.example.backend_timetable.collection.Group;
import com.example.backend_timetable.collection.Session;



@Service
public class GroupService {
   
    @Autowired
    private SessionRepository sessionRepository;

    public Session addGroupToDepartmentInSession(String sessionId, String departmentId, Group data) {

        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {

            return null;
        }
        Session session = sessionOptional.get();


        Optional<Department> departmentOptional = session.getDepartment().stream()
            .filter(dept -> dept.getDepartmentId().equals(departmentId))
            .findFirst();
        
        if (!departmentOptional.isPresent()) {
   
            return null;
        }
        Department department = departmentOptional.get();

      
        Group group = new Group();
        group.setGroupId(UUID.randomUUID().toString());
        group.setGroupName(data.getGroupName());     
        group.setNumberGroups(data.getNumberGroups());
        department.getGroups().add(group);             
        sessionRepository.save(session);

 
        return session;
    }

    public ResponseEntity<String> updateGroupInDepartment(String sessionId, String departmentId, String groupId, Group updatedGroupData) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }
        Session session = sessionOptional.get();
        Optional<Department> departmentOptional = session.getDepartment().stream()
            .filter(dept -> dept.getDepartmentId().equals(departmentId))
            .findFirst();

        if (!departmentOptional.isPresent()) {
            return new ResponseEntity<>("Department not found", HttpStatus.NOT_FOUND);
        }
        Department department = departmentOptional.get();
        Optional<Group> groupOptional = department.getGroups().stream()
            .filter(grp -> grp.getGroupId().equals(groupId))
            .findFirst();
        if (!groupOptional.isPresent()) {
            return new ResponseEntity<>("Group not found", HttpStatus.NOT_FOUND);
        }
        Group group = groupOptional.get();
        group.setGroupName(updatedGroupData.getGroupName());
        group.setNumberGroups(updatedGroupData.getNumberGroups());;
        sessionRepository.save(session);

        return new ResponseEntity<>("Group updated successfully", HttpStatus.OK);
    }

    public ResponseEntity<?> getAllGroupsInDepartment(String sessionId, String departmentId) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }
        Session session = sessionOptional.get();

        Optional<Department> departmentOptional = session.getDepartment().stream()
            .filter(dept -> dept.getDepartmentId().equals(departmentId))
            .findFirst();

        if (!departmentOptional.isPresent()) {
            return new ResponseEntity<>("Department not found", HttpStatus.NOT_FOUND);
        }

        Department department = departmentOptional.get();
        return new ResponseEntity<>(department.getGroups(), HttpStatus.OK);
    }


    public ResponseEntity<?> getGroupById(String sessionId, String departmentId, String groupId) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }
        Session session = sessionOptional.get();
        Optional<Department> departmentOptional = session.getDepartment().stream()
            .filter(dept -> dept.getDepartmentId().equals(departmentId))
            .findFirst();

        if (!departmentOptional.isPresent()) {
            return new ResponseEntity<>("Department not found", HttpStatus.NOT_FOUND);
        }
        Department department = departmentOptional.get();
        Optional<Group> groupOptional = department.getGroups().stream()
            .filter(grp -> grp.getGroupId().equals(groupId))
            .findFirst();

        if (!groupOptional.isPresent()) {
            return new ResponseEntity<>("Group not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(groupOptional.get(), HttpStatus.OK);
    }


    public ResponseEntity<String> deleteGroupFromDepartment(String sessionId, String departmentId, String groupId) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }
        Session session = sessionOptional.get();

        Optional<Department> departmentOptional = session.getDepartment().stream()
            .filter(dept -> dept.getDepartmentId().equals(departmentId))
            .findFirst();

        if (!departmentOptional.isPresent()) {
            return new ResponseEntity<>("Department not found", HttpStatus.NOT_FOUND);
        }
        Department department = departmentOptional.get();
        boolean removed = department.getGroups().removeIf(grp -> grp.getGroupId().equals(groupId));

        if (!removed) {
            return new ResponseEntity<>("Group not found", HttpStatus.NOT_FOUND);
        }
        sessionRepository.save(session);
        return new ResponseEntity<>("Group deleted successfully", HttpStatus.OK);
    }


  
}