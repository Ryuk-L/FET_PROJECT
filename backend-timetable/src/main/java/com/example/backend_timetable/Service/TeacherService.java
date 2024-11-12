package com.example.backend_timetable.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.backend_timetable.Repository.SessionRepository;
import com.example.backend_timetable.collection.Session;
import com.example.backend_timetable.collection.Subjects;
import com.example.backend_timetable.collection.Teacher;
import com.example.backend_timetable.collection.TimeSlotTeacher;
import java.util.Iterator;
import java.util.UUID;

import javax.security.auth.Subject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class TeacherService {

    @Autowired
    private SessionRepository sessionRepository;

    public ResponseEntity<String> addTeacherToSession(String sessionId, Teacher data) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }
        Teacher teacher = new Teacher();
        teacher.setId(UUID.randomUUID().toString());
        teacher.setTeacherName(data.getTeacherName());
        teacher.setSubjectsCanTeach(data.getSubjectsCanTeach());
        teacher.setTimeSlots(data.getTimeSlots());
        teacher.setCin(data.getCin());
        teacher.setEmail(data.getEmail());
        Session session = sessionOptional.get();
        session.getTeachers().add(teacher);
        sessionRepository.save(session);

        return new ResponseEntity<>("Teacher added to session successfully", HttpStatus.OK);
    }


    public ResponseEntity<List<Teacher>> getAllTeachersInSession(String sessionId) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Session session = sessionOptional.get();
        return new ResponseEntity<>(session.getTeachers(), HttpStatus.OK);
    }

    public ResponseEntity<Teacher> getTeacherByIdInSession(String sessionId, String teacherId) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Session session = sessionOptional.get();
        Optional<Teacher> teacherOptional = session.getTeachers().stream()
                .filter(teacher -> teacher.getId().equals(teacherId))
                .findFirst();

        if (!teacherOptional.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(teacherOptional.get(), HttpStatus.OK);
    }

    public ResponseEntity<String> updateTeacherInSession(String sessionId, String teacherId, Teacher updatedTeacher) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }
        Session session = sessionOptional.get();
        List<Teacher> teachers = session.getTeachers();
        for (int i = 0; i < teachers.size(); i++) {
            if (teachers.get(i).getId().equals(teacherId)) {
                teachers.set(i, updatedTeacher);
                sessionRepository.save(session);
                return new ResponseEntity<>("Teacher updated successfully", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Teacher not found", HttpStatus.NOT_FOUND);
    }

    // Method to delete a teacher from a session
    public ResponseEntity<String> deleteTeacherFromSession(String sessionId, String teacherId) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }
        Session session = sessionOptional.get();
        boolean removed = session.getTeachers().removeIf(teacher -> teacher.getId().equals(teacherId));
        if (!removed) {
            return new ResponseEntity<>("Teacher not found", HttpStatus.NOT_FOUND);
        }
        sessionRepository.save(session);
        return new ResponseEntity<>("Teacher deleted successfully", HttpStatus.OK);
    }


    public ResponseEntity<String> addTeachersFromExcel(String sessionId, MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
        
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
        
            if (rows.hasNext()) {
                rows.next();
            }
            while (rows.hasNext()) {
                Row currentRow = rows.next();
        
                Cell nameCell = currentRow.getCell(0);
                String teacherName = getStringCellValue(nameCell);
        
                Cell subjectsCell = currentRow.getCell(1);
                String subjects = getStringCellValue(subjectsCell);
        
                Cell timeSlotCell = currentRow.getCell(2);
                String timeSlots = getStringCellValue(timeSlotCell);
        
                Cell emailCell = currentRow.getCell(3);
                String email = getStringCellValue(emailCell);
        
                Cell cinCell = currentRow.getCell(4);
                String cin = getStringCellValue(cinCell);
        
                Teacher teacher = new Teacher();
                teacher.setCin(cin);
                teacher.setTeacherName(teacherName);
                teacher.setEmail(email);
        
                teacher.setSubjectsCanTeach(convertStringSubjectToList(subjects));
                teacher.setTimeSlots(convertStringToTimeSlots(timeSlots));
                Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
                if (sessionOptional.isPresent()) {
                    Session session = sessionOptional.get();
                    session.getTeachers().add(teacher);
                    sessionRepository.save(session);
                }
            }
            workbook.close();
            return new ResponseEntity<>("Teachers added from Excel successfully", HttpStatus.OK);
        
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to read Excel file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private List<Subjects> convertStringSubjectToList(String subjects) {
        if (subjects == null || subjects.isEmpty()) {
            return new ArrayList<>();
        }
        String[] items = subjects.split(",");
        List<Subjects> result = new ArrayList<>();
        
        for (String item : items) {
            Subjects subject = new Subjects();
            subject.setSubjectName(item.trim());
            result.add(subject);
        }
        return result;
    }
    private List<TimeSlotTeacher> convertStringToTimeSlots(String timeSlots) {
        if (timeSlots == null || timeSlots.isEmpty()) {
            return new ArrayList<>();
        }
        String[] items = timeSlots.split(",");
        List<TimeSlotTeacher> result = new ArrayList<>();
        
        for (String item : items) {
            String[] timeSlotParts = item.trim().split(" ");
            if (timeSlotParts.length == 2) {
                String day = timeSlotParts[0];
                String timeRange = timeSlotParts[1];
        
                String[] timeParts = timeRange.split("-");
                if (timeParts.length == 2) {
                    String startTime = timeParts[0].trim();
                    String endTime = timeParts[1].trim();
        
                    TimeSlotTeacher timeSlotTeacher = new TimeSlotTeacher();
                    timeSlotTeacher.setDay(day);
                    timeSlotTeacher.setStartTime(startTime);
                    timeSlotTeacher.setEndTime(endTime);
                    result.add(timeSlotTeacher);
                }
            }
        }
        return result;
    }
    
    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return "N/A";
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "N/A";
        }
    }
    
    
}
