package com.example.backend_timetable.Repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.backend_timetable.collection.Room;


@Repository
public interface  RoomRepository  extends MongoRepository<Room,String> {

}
