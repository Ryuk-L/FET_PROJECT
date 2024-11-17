package com.example.backend_timetable.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.example.backend_timetable.DTO.SubjectDTO;
import com.example.backend_timetable.Service.SubjectsService;
import com.example.backend_timetable.collection.Subjects;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin/session")
public class SubjectsController {

    @Autowired
    private SubjectsService subjectsService;

    // add subject to session by ID
    @PostMapping("/{sessionId}/subject")
    public ResponseEntity<String> addSubjectToSession(@PathVariable String sessionId, @RequestBody SubjectDTO subjectDTO) {
        return subjectsService.addSubjectToSession(sessionId, subjectDTO);
    }


    @PutMapping("/{sessionId}/subject/{subjectId}")
    public ResponseEntity<String> updateSubject(@PathVariable String sessionId, @PathVariable String subjectId, @RequestBody SubjectDTO subjectDTO) {
        return subjectsService.updateSubjectById(sessionId, subjectId, subjectDTO);
    }

    @DeleteMapping("/{sessionId}/subject/{subjectId}")
    public ResponseEntity<String> deleteSubject(@PathVariable String sessionId, @PathVariable String subjectId) {
        return subjectsService.deleteSubjectById(sessionId, subjectId);
    }

    @GetMapping("/{sessionId}/subject/{subjectId}")
    public ResponseEntity<Subjects> getSubject(@PathVariable String sessionId, @PathVariable String subjectId) {
        return subjectsService.getSubjectById(sessionId, subjectId);
    }

    @GetMapping("/{sessionId}/subjects")
    public ResponseEntity<List<Subjects>> getAllSubjects(@PathVariable String sessionId) {
    return subjectsService.getAllSubjectsBySessionId(sessionId);
}




}