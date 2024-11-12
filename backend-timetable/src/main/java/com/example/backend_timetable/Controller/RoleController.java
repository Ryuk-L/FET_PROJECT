package com.example.backend_timetable.Controller;


import com.example.backend_timetable.Service.RoleService;
import com.example.backend_timetable.collection.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // add role
    @PostMapping("/add")
    public ResponseEntity<Role> addRole(@RequestBody Role role) {
        Role newRole = roleService.addRole(role);
        return ResponseEntity.ok(newRole);
    }

    // update role user or session list
    @PutMapping("/update/{idUser}")
    public ResponseEntity<Role> updateRole(@PathVariable String idUser, @RequestBody Role role) {
        Role updatedRole = roleService.updateRole(idUser, role.getRoleUser(), role.getSessionList());
        return ResponseEntity.ok(updatedRole);
    }


    // get role user by id 
    @GetMapping("/{idUser}")
    public ResponseEntity<Role> getRoleByIdUser(@PathVariable String idUser) {
        Role role = roleService.getRoleByIdUser(idUser);
        return ResponseEntity.ok(role);
    }
}
