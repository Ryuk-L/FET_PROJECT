package com.example.backend_timetable.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.backend_timetable.DTO.AuthRequest;
import com.example.backend_timetable.DTO.AuthResponse;
import com.example.backend_timetable.Repository.SessionRepository;
import com.example.backend_timetable.collection.Role;
import com.example.backend_timetable.collection.Session;
import com.example.backend_timetable.collection.Subjects;
import com.example.backend_timetable.collection.Teacher;
import com.example.backend_timetable.collection.TimeSlotTeacher;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Iterator;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class TeacherService {

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private FirebaseService firebaseAuthService;
    @Autowired
    private RoleService roleService;

    public ResponseEntity<String> addTeacherToSession(String sessionId, Teacher data) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }
        
        Session session = sessionOptional.get();
        boolean teacherExists = session.getTeachers().stream()
                .anyMatch(existingTeacher -> 
                    existingTeacher.getCin().equals(data.getCin()) || 
                    existingTeacher.getTeacherName().equals(data.getTeacherName()) ||
                    existingTeacher.getEmail().equals(data.getEmail())
                );
        
        if (teacherExists) {
            return new ResponseEntity<>("Teacher with the same email or CIN or Name already exists in the session", HttpStatus.CONFLICT);
        }
        

       
        AuthRequest userFirebase = new AuthRequest();
        userFirebase.setEmail(data.getEmail());
        userFirebase.setPassword(data.getCin());

        AuthResponse authResponse = firebaseAuthService.createUser(userFirebase);
    
        if (authResponse.isError()) {
            return new ResponseEntity<>("Error registering user in Firebase: " + authResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    
      
        String firebaseUid = authResponse.getUid();
    
        
        Teacher teacher = new Teacher();
        teacher.setId(firebaseUid); 
        teacher.setTeacherName(data.getTeacherName());
        teacher.setCin(data.getCin());
        teacher.setEmail(data.getEmail());
    
       
        Role roleUser = new Role();
        roleUser.setIdUser(firebaseUid);
        roleUser.setRoleUser("Teacher");
        roleUser.getSessionList().add(sessionId);
        roleService.addRole(roleUser);

    
       
        // Session session = sessionOptional.get();
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

 

    
public ResponseEntity<String> deleteTeacherFromSession(String sessionId, String teacherId) {
    Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
    if (!sessionOptional.isPresent()) {
        return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
    }

    Session session = sessionOptional.get();

    boolean removed = session.getTeachers().removeIf(teacher -> teacher.getId().equals(teacherId));
    if (!removed) {
        return new ResponseEntity<>("Teacher not found in session", HttpStatus.NOT_FOUND);
    }

    try {
        roleService.deleteRoleById(teacherId);
    } catch (Exception e) {
        return new ResponseEntity<>("Error deleting teacher's role: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    try {
        FirebaseAuth.getInstance().deleteUser(teacherId);
    } catch (Exception e) {
        return new ResponseEntity<>("Error deleting teacher from Firebase: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    sessionRepository.save(session);

    return new ResponseEntity<>("Teacher deleted successfully from session, role, and Firebase", HttpStatus.OK);
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
    
               
                String teacherName = getStringCellValue(currentRow.getCell(0));
                String email = getStringCellValue(currentRow.getCell(1));
                String cin = getStringCellValue(currentRow.getCell(2));
    
            
                AuthRequest userFirebase = new AuthRequest();
                userFirebase.setEmail(email);
                userFirebase.setPassword(cin);
                AuthResponse authResponse = firebaseAuthService.createUser(userFirebase);
    
                
                if (authResponse.getMessage() != null && authResponse.getMessage().startsWith("Error")) {
                    return new ResponseEntity<>("Error registering user in Firebase: " + authResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
    
       
                Teacher teacher = new Teacher();
                teacher.setId(authResponse.getUid()); 
                teacher.setCin(cin);
                teacher.setTeacherName(teacherName);
                teacher.setEmail(email);
    
              
                Role roleUser = new Role();
                roleUser.setIdUser(authResponse.getUid());
                roleUser.setRoleUser("Teacher");
                roleService.addRole(roleUser);
    
               
                System.out.println("\n\n-----------------------------------------");
                System.out.println("cin: " + cin);
                System.out.println("email: " + email);
                System.out.println("teacher name: " + teacherName);
                System.out.println("-----------------------------------------\n\n");
    
               
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


    public ResponseEntity<String> updateTeacherInSession(String sessionId, String teacherId, Teacher updatedTeacherData) {
       
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }
        Session session = sessionOptional.get();
    
        boolean teacherExists = session.getTeachers().stream()
        .anyMatch(existingTeacher -> 
            (existingTeacher.getCin().equals(updatedTeacherData.getCin()) || 
             existingTeacher.getTeacherName().equals(updatedTeacherData.getTeacherName()) || 
             existingTeacher.getEmail().equals(updatedTeacherData.getEmail())) &&
            !existingTeacher.getId().equals(teacherId)
        );
        
        if (teacherExists) {
            return new ResponseEntity<>("Teacher with the same email, CIN, or Name already exists in the session", HttpStatus.CONFLICT);
        }

    
        
        Optional<Teacher> teacherOptional = session.getTeachers().stream()
                .filter(teacher -> teacher.getId().equals(teacherId))
                .findFirst();
    
        if (!teacherOptional.isPresent()) {
            return new ResponseEntity<>("Teacher not found", HttpStatus.NOT_FOUND);
        }
        Teacher teacher = teacherOptional.get();
    
        
        teacher.setTeacherName(updatedTeacherData.getTeacherName());
        teacher.setCin(updatedTeacherData.getCin());
        teacher.setEmail(updatedTeacherData.getEmail());
        teacher.setSubjectsCanTeach(updatedTeacherData.getSubjectsCanTeach());
        teacher.setTimeSlots(updatedTeacherData.getTimeSlots());
    
        sessionRepository.save(session);
    
        return new ResponseEntity<>("Teacher updated successfully in session", HttpStatus.OK);
    }
    
    
    
    
}
