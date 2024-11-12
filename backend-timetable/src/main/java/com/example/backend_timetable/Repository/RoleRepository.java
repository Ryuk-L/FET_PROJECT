package com.example.backend_timetable.Repository;
import com.example.backend_timetable.collection.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;


public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByIdUser(String idUser);
}