package com.example.backend_timetable.Service;


import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import com.example.backend_timetable.DTO.SubjectDTO;
import com.example.backend_timetable.Repository.SessionRepository;
import com.example.backend_timetable.Repository.SubjectsRepository;

import com.example.backend_timetable.collection.Session;
import com.example.backend_timetable.collection.Subjects;
import java.util.UUID;


@Service
public class SubjectsService {

    @Autowired
    private SubjectsRepository subjectRepository;
     @Autowired
    private SessionRepository sessionRepository;

    public ResponseEntity<String> addSubjectToSession(String sessionId, SubjectDTO subjectDTO) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }
        Session session = sessionOptional.get();
        Subjects subject = new Subjects();
        subject.setId(UUID.randomUUID().toString());
        subject.setSubjectName(subjectDTO.getSubjectName());
        subject.setDuration(subjectDTO.getDuration());
        subject.setType(subjectDTO.getType());
        session.getSubjects().add(subject);
        sessionRepository.save(session);
        return new ResponseEntity<>("Subject added to session", HttpStatus.OK);
    }


    public ResponseEntity<String> updateSubjectById(String sessionId, String subjectId, SubjectDTO subjectDTO) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }
        
        Session session = sessionOptional.get();
        for (Subjects subject : session.getSubjects()) {
            if (subject.getId().equals(subjectId)) {
                subject.setSubjectName(subjectDTO.getSubjectName());
                subject.setDuration(subjectDTO.getDuration());
                subject.setType(subjectDTO.getType());
                sessionRepository.save(session);
                return new ResponseEntity<>("Subject updated", HttpStatus.OK);
            }
        }
        
        return new ResponseEntity<>("Subject not found", HttpStatus.NOT_FOUND);
    }


    public ResponseEntity<String> deleteSubjectById(String sessionId, String subjectId) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }
        
        Session session = sessionOptional.get();
        Subjects subjectToRemove = null;
        
        for (Subjects subject : session.getSubjects()) {
            if (subject.getId().equals(subjectId)) {
                subjectToRemove = subject;
                break;
            }
        }
        
        if (subjectToRemove != null) {
            session.getSubjects().remove(subjectToRemove);
            sessionRepository.save(session);
            return new ResponseEntity<>("Subject deleted", HttpStatus.OK);
        }
        
        return new ResponseEntity<>("Subject not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Subjects> getSubjectById(String sessionId, String subjectId) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Session session = sessionOptional.get();
        for (Subjects subject : session.getSubjects()) {
            if (subject.getId().equals(subjectId)) {
                return new ResponseEntity<>(subject, HttpStatus.OK);
            }
        }
        
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    public ResponseEntity<List<Subjects>> getAllSubjectsBySessionId(String sessionId) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Session session = sessionOptional.get();
        return new ResponseEntity<>(session.getSubjects(), HttpStatus.OK);
    }
    

 
}