package com.example.backend_timetable.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.backend_timetable.DTO.ActiveDaysRequest;
import com.example.backend_timetable.DTO.SessionDTO;
import com.example.backend_timetable.DTO.SessionRequest;
import com.example.backend_timetable.DTO.UniversityNameRequest;
import com.example.backend_timetable.Repository.SessionRepository;
import com.example.backend_timetable.collection.Room;
import com.example.backend_timetable.collection.Session;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    public ResponseEntity<?> createSession(@RequestBody SessionDTO sessionDTO) {
        
        Optional<Session> existingSession = sessionRepository.findByYearAndUniversityName(sessionDTO.getYear(), sessionDTO.getUniversityName());
        if (existingSession.isPresent()) {
            return new ResponseEntity<>("A session with the same year and university name already exists.", HttpStatus.CONFLICT);
        }
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


    public ResponseEntity<SessionDTO> getSessionWithoutLists(String sessionId) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Session session = sessionOptional.get();
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setYear(session.getYear());
        sessionDTO.setUniversityName(session.getUniversityName());
        sessionDTO.setTimeBreakStart(session.getTimeBreakStart());
        sessionDTO.setTimeBreakEnd(session.getTimeBreakEnd());
        sessionDTO.setTimeDayStart(session.getTimeDayStart());
        sessionDTO.setTimeDayEnd(session.getTimeDayEnd());
        sessionDTO.setActiveDays(session.getActiveDays()); 
    
        return new ResponseEntity<>(sessionDTO, HttpStatus.OK);
    }


    public ResponseEntity<String> deleteSessionById(String sessionId) {
        if (!sessionRepository.existsById(sessionId)) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }
        sessionRepository.deleteById(sessionId);
        return new ResponseEntity<>("Session deleted successfully", HttpStatus.OK);
    }




    // public Session updateTimeBreakStart(String sessionId, String timeBreakStart) {
    //    Optional<Session> optionalSession = sessionRepository.findById(sessionId);
    //     if (optionalSession.isPresent()){
    //         Session session = optionalSession.get();
    //         session.setTimeBreakStart(timeBreakStart);
    //         return sessionRepository.save(session);
    //     }
    //     return null;
    // }

    // public Session updateTimeBreakEnd(String sessionId, String timeBreakEnd) {
    //     Optional<Session> optionalSession = sessionRepository.findById(sessionId);
    //      if (optionalSession.isPresent()){
    //          Session session = optionalSession.get();
    //          session.setTimeBreakEnd(timeBreakEnd);
    //          return sessionRepository.save(session);
    //      }
    //      return null;
    //  }


    public ResponseEntity<String> updateTimeBreak(String sessionId, String timeBreakEnd,String timeBreakStart){
        Optional<Session> optionalSession = sessionRepository.findById(sessionId);
        if (optionalSession.isPresent()){
            Session session = optionalSession.get();
            session.setTimeBreakEnd(timeBreakEnd);
            session.setTimeBreakStart(timeBreakStart);
            sessionRepository.save(session);
            return new ResponseEntity<>("Time break has been successfully updated.", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    public ResponseEntity<String> updateTimeDay(String sessionId, String timeDayEnd,String timeDayStart){
        Optional<Session> optionalSession = sessionRepository.findById(sessionId);
        if (optionalSession.isPresent()){
            Session session = optionalSession.get();
            session.setTimeDayEnd(timeDayEnd);
            session.setTimeDayStart(timeDayStart);
            sessionRepository.save(session);
            return new ResponseEntity<>("Time day has been successfully updated from [ "+timeDayStart+"] to ["+timeDayEnd+"].", HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> updateUniversityName(String sessionId, UniversityNameRequest data) {
        Optional<Session> optionalSession = sessionRepository.findById(sessionId);
        if (optionalSession.isPresent()){
            Session session = optionalSession.get();
            session.setUniversityName(data.getUniversityName());
            sessionRepository.save(session);
            return new ResponseEntity<>("University name has been successfully updated.", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    
    public ResponseEntity<String> updateSession(
        @PathVariable String sessionId,
        @RequestBody SessionRequest request) {

    Optional<Session> optionalSession = sessionRepository.findById(sessionId);
    if (optionalSession.isPresent()) {
        Session existingSession = optionalSession.get();

        boolean isUniversityNameOrYearChanged = 
            !existingSession.getUniversityName().equals(request.getUniversityName()) ||
            !existingSession.getYear().equals(request.getYear());

        if (isUniversityNameOrYearChanged) {
            boolean isConflict = sessionRepository.existsByYearAndUniversityName(
                    request.getYear(),
                    request.getUniversityName());

            if (isConflict) {
                return new ResponseEntity<>(
                        "Cannot update: A session with the same university name and year already exists.",
                        HttpStatus.CONFLICT);
            }
        }

        existingSession.setYear(request.getYear());
        existingSession.setUniversityName(request.getUniversityName());
        existingSession.setTimeBreakStart(request.getTimeBreakStart());
        existingSession.setTimeBreakEnd(request.getTimeBreakEnd());
        existingSession.setTimeDayStart(request.getTimeDayStart());
        existingSession.setTimeDayEnd(request.getTimeDayEnd());
        existingSession.setActiveDays(request.getActiveDays());

        sessionRepository.save(existingSession);
        return new ResponseEntity<>("Session updated successfully.", HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}




public ResponseEntity<String> updateActiveDays(
        @PathVariable String sessionId, 
        @RequestBody ActiveDaysRequest activeDays) {

    Optional<Session> optionalSession = sessionRepository.findById(sessionId);
    if (optionalSession.isPresent()) {
        Session session = optionalSession.get();
        session.setActiveDays(activeDays.getActiveDays());
        sessionRepository.save(session); 
        return new ResponseEntity<>("Active days updated successfully.", HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
    }


    


   
}


    






    
}
