package com.example.backend_timetable.Controller;

import com.example.backend_timetable.DTO.RoomDTO;
import com.example.backend_timetable.Repository.SessionRepository;
import com.example.backend_timetable.Service.RoomService;
import com.example.backend_timetable.collection.Room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin/session")
public class RoomController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private SessionRepository sessionRepository;

    // add room to session by ID
    @PostMapping("/{sessionId}/rooms")
    public ResponseEntity<String> addRoomToSession(@PathVariable String sessionId, @RequestBody RoomDTO roomDTO) {
        return roomService.addRoomToSession(sessionId, roomDTO);
    }

    // read all rooms from session by ID
    @GetMapping("/{sessionId}/rooms")
    public ResponseEntity<List<Room>> getRoomsBySessionId(@PathVariable String sessionId) {
        List<Room> rooms = roomService.getRoomsBySessionId(sessionId);
        if (rooms == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    // read room by ID from sessions by ID
    @GetMapping("/{sessionId}/rooms/{roomId}")
    public ResponseEntity<Room> getRoomByIdFromSession(@PathVariable String sessionId, @PathVariable String roomId) {
        return roomService.getRoomByIdFromSession(sessionId, roomId);
    }

    // delete room by ID from sessions by ID
    @DeleteMapping("/{sessionId}/rooms/{roomId}")
    public ResponseEntity<String> deleteRoomByIdFromSession(@PathVariable String sessionId,
            @PathVariable String roomId) {
        return roomService.deleteRoomByIdFromSession(sessionId, roomId);
    }

    // update room by ID from sessions by ID
    @PutMapping("/{sessionId}/rooms/{roomId}")
    public ResponseEntity<String> updateRoomInSession(
            @PathVariable String sessionId,
            @PathVariable String roomId,
            @RequestBody RoomDTO roomDTO) {
        return roomService.updateRoomInSession(sessionId, roomId, roomDTO);
    }

}
