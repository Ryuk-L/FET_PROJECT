package com.example.backend_timetable.Controller;

import com.example.backend_timetable.DTO.RoomDTO;
import com.example.backend_timetable.DTO.SessionDTO;
import com.example.backend_timetable.Entity.Room;
import com.example.backend_timetable.Entity.Session;
import com.example.backend_timetable.Repository.SessionRepository;
import com.example.backend_timetable.Service.SessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/admin/sessions")
public class SessionController {
  


    @Autowired
    private SessionService sessionService;

    @Autowired
    SessionRepository sessionRepository;
  


    @PostMapping
    public ResponseEntity<Session> createSession(@RequestBody SessionDTO sessionDTO) {
      return sessionService.createSession(sessionDTO);
    }


    




}
