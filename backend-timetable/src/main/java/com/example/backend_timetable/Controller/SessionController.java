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
import org.springframework.http.HttpStatus;
import java.util.Optional;
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



  @GetMapping("/{sessionId}/department-count")
    public ResponseEntity<Long> getDepartmentCount(@PathVariable String sessionId) {
   
        long departmentCount = sessionService.getDepartmentCount(sessionId);
        return ResponseEntity.ok(departmentCount); 
    }

    @GetMapping("/{sessionId}/room-count")
    public ResponseEntity<Long> getRoomCount(@PathVariable String sessionId) {
  
        long RoomCount = sessionService.getRoomCount(sessionId);
        return ResponseEntity.ok(RoomCount); 
    }

    @GetMapping("/{sessionId}/subject-lecture-count")
    public ResponseEntity<Long> getLectureSubjectCount(@PathVariable String sessionId) {
        long SubjectCount = sessionService.getLectureSubjectCount(sessionId);
        return ResponseEntity.ok(SubjectCount); 
    }

    @GetMapping("/{sessionId}/subject-lab-count")
    public ResponseEntity<Long> getLabSubjectCount(@PathVariable String sessionId) {
        long SubjectCount = sessionService.getLabSubjectCount(sessionId);
        return ResponseEntity.ok(SubjectCount); 
    }

    @GetMapping("/{sessionId}/teacher-count")
    public ResponseEntity<Long> getTeacherCount(@PathVariable String sessionId) {
        long TeacherCount = sessionService.getTeacherCount(sessionId);
        return ResponseEntity.ok(TeacherCount); 
    }


    @GetMapping("/{sessionId}/group-count")
    public ResponseEntity<Long> getGroupCount(@PathVariable String sessionId) {
        long GroupCount = sessionService.getGroupCount(sessionId);
        return ResponseEntity.ok(GroupCount); 
    }


    @GetMapping("/{sessionId}/student-count")
    public ResponseEntity<Long> getStudnetCount(@PathVariable String sessionId) {
        long StudnetCount = sessionService.getStudentCount(sessionId);
        return ResponseEntity.ok(StudnetCount); 
    }

}
