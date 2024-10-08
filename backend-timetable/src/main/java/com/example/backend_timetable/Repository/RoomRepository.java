package com.example.backend_timetable.Repository;


import com.example.backend_timetable.Entity.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface  RoomRepository  extends MongoRepository<Room,String> {

}
