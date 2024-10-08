package com.example.backend_timetable.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.backend_timetable.collection.Session;


@Repository
public interface SessionRepository extends MongoRepository<Session,String> {
}
