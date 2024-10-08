package com.example.backend_timetable.Service;

import java.util.List;
import java.util.Optional;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.backend_timetable.Repository.SubjectRepository;
import com.example.backend_timetable.collection.subject;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    // // Create a new subject
    // public ResponseEntity<Subject> createSubject(Subject subject) {
    //     Subject savedSubject = subjectRepository.save(subject);
    //     return new ResponseEntity<>(savedSubject, HttpStatus.CREATED);
    // }

    // // Get all subjects
    // public ResponseEntity<List<Subject>> getAllSubjects() {
    //     List<Subject> subjects = subjectRepository.findAll();
    //     return new ResponseEntity<>(subjects, HttpStatus.OK);
    // }

    // // Get a subject by ID
    // public ResponseEntity<Subject> getSubjectById(String id) {
    //     Optional<Subject> optionalSubject = subjectRepository.findById(id);
    //     return optionalSubject.map(subject -> new ResponseEntity<>(subject, HttpStatus.OK))
    //             .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    // }

    // // Update a subject
    // public ResponseEntity<Subject> updateSubject(String id, Subject subjectDetails) {
    //     Optional<Subject> optionalSubject = subjectRepository.findById(id);
    //     if (optionalSubject.isPresent()) {
    //         Subject subject = optionalSubject.get();
    //         subject.setSubjectName(subjectDetails.getSubjectName());
    //         subject.setDuration(subjectDetails.getDuration());
    //         return new ResponseEntity<>(subjectRepository.save(subject), HttpStatus.OK);
    //     }
    //     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    // }

    // // Delete a subject
    // public ResponseEntity<Void> deleteSubject(String id) {
    //     if (subjectRepository.existsById(id)) {
    //         subjectRepository.deleteById(id);
    //         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    //     }
    //     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    // }
}