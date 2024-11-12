package com.example.backend_timetable.Service;
import com.example.backend_timetable.Repository.RoleRepository;
import com.example.backend_timetable.collection.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;


    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    public Role updateRole(String idUser, String newRoleUser, List<String> newSessionList) {
        Optional<Role> existingRole = roleRepository.findByIdUser(idUser);
        if (existingRole.isPresent()) {
            Role role = existingRole.get();
            role.setRoleUser(newRoleUser);
            role.setSessionList(newSessionList);
            return roleRepository.save(role);
        } else {
            throw new RuntimeException("Role with idUser: " + idUser + " not found.");
        }
    }

    public Role getRoleByIdUser(String idUser) {
        return roleRepository.findByIdUser(idUser)
                .orElseThrow(() -> new RuntimeException("Role with idUser: " + idUser + " not found."));
    }
}
