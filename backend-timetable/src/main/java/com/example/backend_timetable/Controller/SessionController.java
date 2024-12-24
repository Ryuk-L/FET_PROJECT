package com.example.backend_timetable.Controller;

import com.example.backend_timetable.DTO.ActiveDaysRequest;
import com.example.backend_timetable.DTO.SessionDTO;
import com.example.backend_timetable.DTO.SessionRequest;
import com.example.backend_timetable.DTO.TimeBreakRequest;
import com.example.backend_timetable.DTO.TimeDayRequest;
import com.example.backend_timetable.DTO.UniversityNameRequest;
import com.example.backend_timetable.Repository.SessionRepository;
import com.example.backend_timetable.Service.SessionService;
import com.example.backend_timetable.collection.Session;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin/sessions")
public class SessionController {

  @Autowired
  private SessionService sessionService;

  @Autowired
  SessionRepository sessionRepository;

  // create session
  @PostMapping
  public ResponseEntity<?> createSession(@RequestBody SessionDTO sessionDTO) {
    return sessionService.createSession(sessionDTO);
  }
  // delete session
  @DeleteMapping("{sessionId}")
    public ResponseEntity<String> deleteSession(@PathVariable String sessionId) {
        return sessionService.deleteSessionById(sessionId);
    }

  // get session by ID
  @GetMapping("/{sessionId}")
  public ResponseEntity<SessionDTO> getSessionById(@PathVariable String sessionId) {
      return sessionService.getSessionWithoutLists(sessionId);
  }

   // update session
   @PutMapping("/{sessionId}")
   public ResponseEntity<String> updateSession(@PathVariable String sessionId,@RequestBody SessionRequest request) {
    return sessionService.updateSession(sessionId, request);
  }

  // update Time break
  @PutMapping("/{sessionId}/TimeBreak")
  public ResponseEntity<String> updateTimeBreak(@PathVariable String sessionId, @RequestBody TimeBreakRequest time) {
    return sessionService.updateTimeBreak(sessionId, time.getTimeBreakEnd(), time.getTimeBreakStart());
  }

  // update Time day
  @PutMapping("/{sessionId}/TimeDay")
  public ResponseEntity<String> updateTimeDay(@PathVariable String sessionId, @RequestBody TimeDayRequest time) {
    return sessionService.updateTimeDay(sessionId, time.getTimeDayEnd(), time.getTimeDayStart());
  }

  // update university name
  @PutMapping("/{sessionId}/universityName")
  public ResponseEntity<String> updateUniversityName(@PathVariable String sessionId,
      @RequestBody UniversityNameRequest universityName) {
    return sessionService.updateUniversityName(sessionId, universityName);
  }

  //update active Days
  @PutMapping("/{sessionId}/activeDays")
  public ResponseEntity<String> updateActiveDays(@PathVariable String sessionId, @RequestBody ActiveDaysRequest activeDays){
    return sessionService.updateActiveDays(sessionId, activeDays);
  }




}
