package com.example.backend_timetable.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_timetable.Service.GroupService;
import com.example.backend_timetable.collection.Group;
import com.example.backend_timetable.collection.Session;

@RestController
@RequestMapping("/admin/session")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @PostMapping("/{sessionId}/departments/{departmentId}/groups")
    public ResponseEntity<String> addGroupToDepartment(
            @PathVariable String sessionId,
            @PathVariable String departmentId,
            @RequestBody Group groupData) {

        Session updatedSession = groupService.addGroupToDepartmentInSession(sessionId, departmentId, groupData);
        
        if (updatedSession == null) {
            return new ResponseEntity<>("Session or Department not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Group added to department", HttpStatus.OK);
    }


    @PutMapping("/{sessionId}/departments/{departmentId}/groups/{groupId}")
    public ResponseEntity<String> updateGroupInDepartment(
            @PathVariable String sessionId,
            @PathVariable String departmentId,
            @PathVariable String groupId,
            @RequestBody Group updatedGroupData) {

        return groupService.updateGroupInDepartment(sessionId, departmentId, groupId, updatedGroupData);
    }


     @GetMapping("/{sessionId}/departments/{departmentId}/groups")
    public ResponseEntity<?> getAllGroups(
            @PathVariable String sessionId,
            @PathVariable String departmentId) {
        return groupService.getAllGroupsInDepartment(sessionId, departmentId);
    }
    

    @GetMapping("/{sessionId}/departments/{departmentId}/groups/{groupId}")
    public ResponseEntity<?> getGroupById(
            @PathVariable String sessionId,
            @PathVariable String departmentId,
            @PathVariable String groupId) {
        return groupService.getGroupById(sessionId, departmentId, groupId);
    }



    @DeleteMapping("/{sessionId}/departments/{departmentId}/groups/{groupId}")
    public ResponseEntity<String> deleteGroup(
            @PathVariable String sessionId,
            @PathVariable String departmentId,
            @PathVariable String groupId) {
        return groupService.deleteGroupFromDepartment(sessionId, departmentId, groupId);
    }
}
