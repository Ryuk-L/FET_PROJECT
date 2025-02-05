package com.example.backend_timetable.Service;

import com.example.backend_timetable.DTO.RoomDTO;
import com.example.backend_timetable.Repository.RoomRepository;
import com.example.backend_timetable.Repository.SessionRepository;
import com.example.backend_timetable.collection.Room;
import com.example.backend_timetable.collection.Session;
import com.example.backend_timetable.collection.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoomService {

    @Autowired
    private SessionRepository sessionRepository;

    public ResponseEntity<String> addRoomToSession(String sessionId, RoomDTO roomDTO) {
        // Fetch the session by ID
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }
        Session session = sessionOptional.get();

      
    
        // Check for duplicate room name within the session
        boolean roomExists = session.getRooms().stream()
        .anyMatch(existingRoom -> existingRoom.getNameRoom().equals(roomDTO.getNameRoom()));
        if (roomExists) {
            return new ResponseEntity<>("Room with the same name already exists in the session", HttpStatus.CONFLICT);
        }
    
        // Create and add the new room
        Room room = new Room();
        room.setRoomId(UUID.randomUUID().toString());
        room.setNameRoom(roomDTO.getNameRoom());
        room.setCapacity(roomDTO.getCapacity());
        room.setType(roomDTO.getType());
    
        session.getRooms().add(room);
        sessionRepository.save(session);
    
        return new ResponseEntity<>("Room added to session successfully", HttpStatus.OK);
    }
    

    public List<Room> getRoomsBySessionId(String sessionId) {
        Optional<Session> optionalSession = sessionRepository.findById(sessionId);
        if (optionalSession.isPresent())
            return optionalSession.get().getRooms();

        else
            return null;
    }

    public ResponseEntity<Room> getRoomByIdFromSession(String sessionId, String roomId) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (sessionOptional.isPresent()) {
            Session session = sessionOptional.get();
            for (Room room : session.getRooms()) {
                if (room.getRoomId().equals(roomId)) {
                    return new ResponseEntity<>(room, HttpStatus.OK);

                }
            }

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    public ResponseEntity<String> deleteRoomByIdFromSession(String sessionId, String roomId) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (sessionOptional.isPresent()) {
            Session session = sessionOptional.get();
            for (Room room : session.getRooms()) {
                if (room.getRoomId().equals(roomId)) {
                    session.getRooms().remove(room);
                    sessionRepository.save(session);
                    return new ResponseEntity<>("Room deleted from session", HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> updateRoomInSession(String sessionId, String roomId, RoomDTO roomDTO) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }

        Session session = sessionOptional.get();
        boolean roomExists = session.getRooms().stream()
        .anyMatch(existingRoom -> !existingRoom.getRoomId().equals(roomId) &&
                                   existingRoom.getNameRoom().equals(roomDTO.getNameRoom()));
    if (roomExists) {
        return new ResponseEntity<>("Room with the same name already exists in the session", HttpStatus.CONFLICT);
    }
        for (Room room : session.getRooms()) {
            if (room.getRoomId().equals(roomId)) {
                room.setNameRoom(roomDTO.getNameRoom());
                room.setCapacity(roomDTO.getCapacity());
                room.setType(roomDTO.getType());
                sessionRepository.save(session);
                return new ResponseEntity<>("Room updated successfully", HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("Room not found", HttpStatus.NOT_FOUND);
    }

}
