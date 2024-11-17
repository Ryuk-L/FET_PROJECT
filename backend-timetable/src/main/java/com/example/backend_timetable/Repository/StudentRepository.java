package com.example.backend_timetable.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.backend_timetable.collection.Student;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    boolean existsByName(String name);
}
