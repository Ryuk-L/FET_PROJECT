package com.example.backend_timetable.Service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.backend_timetable.DTO.SessionDTO;
import com.example.backend_timetable.Entity.Session;
import com.example.backend_timetable.Repository.SessionRepository;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

     public ResponseEntity<Session> createSession(@RequestBody SessionDTO sessionDTO) {
        Session session = new Session();
        session.setYear(sessionDTO.getYear());
        session.setUniversityName(sessionDTO.getUniversityName());
        session.setTimeBreakStart(sessionDTO.getTimeBreakStart());
        session.setTimeBreakEnd(sessionDTO.getTimeBreakEnd());
        session.setTimeDayStart(sessionDTO.getTimeDayStart());
        session.setTimeDayEnd(sessionDTO.getTimeDayEnd());
        session.setActiveDays(sessionDTO.getActiveDays());
        session.setRooms(new ArrayList<>());
        sessionRepository.save(session);
        return new ResponseEntity<>(session, HttpStatus.CREATED);
    }



    
}
