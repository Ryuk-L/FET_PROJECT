package com.example.backend_timetable.Repository;

import com.example.backend_timetable.Entity.Session;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SessionRepository extends MongoRepository<Session,String> {
}
