package com.example.backend_timetable.Service;

import com.example.backend_timetable.DTO.AuthRequest;
import com.example.backend_timetable.DTO.AuthResponse;
import com.example.backend_timetable.Repository.SessionRepository;
import com.example.backend_timetable.Repository.StudentRepository;
import com.example.backend_timetable.collection.Department;
import com.example.backend_timetable.collection.Group;
import com.example.backend_timetable.collection.Role;
import com.example.backend_timetable.collection.Session;
import com.example.backend_timetable.collection.Student;
import com.google.firebase.auth.FirebaseAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class StudentService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private RoleService roleService;

    public ResponseEntity<String> addStudentToGroup(String sessionId, String departmentId, String groupId,
            Student data) {

        try {
            Session session = findSessionById(sessionId);
            if (session == null) {
                return new ResponseEntity<>("Session not found with id: " + sessionId, HttpStatus.NOT_FOUND);
            }
            Department department = findDepartmentById(session, departmentId);
            if (department == null) {
                return new ResponseEntity<>("Department not found with id: " + departmentId, HttpStatus.NOT_FOUND);
            }

            Group group = findGroupById(department, groupId);
            if (group == null) {
                return new ResponseEntity<>("Group not found with id: " + groupId, HttpStatus.NOT_FOUND);
            }
 

            for (Department dept : session.getDepartment()) {
                for (Group existingGroup : dept.getGroups()) {
                    for(Student existingStudent : existingGroup.getStudents()) {
                        if (existingStudent.getName().equals(data.getName()) ||
                        existingStudent.getCin().equals(data.getCin()) ||
                        existingStudent.getEmail().equals(data.getEmail())) {
                            return new ResponseEntity<>(" Student with the same Email or CIN or Name already exists in another group.", HttpStatus.CONFLICT);
                        }
                    }
                }
            }

    
            

            AuthRequest userFirebase = new AuthRequest();
            userFirebase.setEmail(data.getEmail());
            userFirebase.setPassword(data.getCin());

            AuthResponse authResponse = firebaseService.createUser(userFirebase);

            if (authResponse.isError()) {
                return new ResponseEntity<>("Error registering user in Firebase: " + authResponse.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String firebaseUid = authResponse.getUid();

            Student student = new Student();
            student.setEmail(data.getEmail());
            student.setName(data.getName());
            student.setId(firebaseUid);
            student.setGroup(data.getGroup());
            student.setCin(data.getCin());

            group.getStudents().add(student);
            sessionRepository.save(session);
            Role roleUser = new Role();
            roleUser.setIdUser(firebaseUid);
            roleUser.setRoleUser("Student");
            roleService.addRole(roleUser);

            return new ResponseEntity<>("Student added to group successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> updateStudent(String sessionId, String departmentId, String groupId, String studentId,
            Student updatedStudent) {
        Session session = findSessionById(sessionId);
        Department department = findDepartmentById(session, departmentId);
        Group group = findGroupById(department, groupId);

        for (Department dept : session.getDepartment()) {
            for (Group existingGroup : dept.getGroups()) {
                for (Student existingStudent : existingGroup.getStudents()) {
                    // Check if the student name, CIN or email already exists in another group
                    if ((existingStudent.getName().equals(updatedStudent.getName()) ||
                         existingStudent.getCin().equals(updatedStudent.getCin())) &&
                         !existingStudent.getId().equals(studentId)) {
                        return new ResponseEntity<>("Student with the same Email, CIN, or Name already exists in another group.", HttpStatus.CONFLICT);
                    }
                }
            }
        }
        
        

        group.getStudents().stream()
                .filter(student -> student.getId().equals(studentId))
                .findFirst()
                .ifPresent(student -> {
                    student.setName(updatedStudent.getName());
                    student.setEmail(updatedStudent.getEmail());
                    student.setCin(updatedStudent.getCin());
                    student.setGroup(updatedStudent.getGroup());
                });
        sessionRepository.save(session);

        return new ResponseEntity<>("Student updated to group successfully", HttpStatus.OK);
    }

    public Student getStudent(String sessionId, String departmentId, String groupId, String studentId) {
        Session session = findSessionById(sessionId);
        Department department = findDepartmentById(session, departmentId);
        Group group = findGroupById(department, groupId);

        return group.getStudents().stream()
                .filter(student -> student.getId().equals(studentId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public ResponseEntity<String> deleteStudent(String sessionId, String departmentId, String groupId,
            String studentId) {

        Session session = findSessionById(sessionId);
        if (session == null) {
            return new ResponseEntity<>("Session not found with id: " + sessionId, HttpStatus.NOT_FOUND);
        }

        Department department = findDepartmentById(session, departmentId);
        if (department == null) {
            return new ResponseEntity<>("Department not found with id: " + departmentId, HttpStatus.NOT_FOUND);
        }

        Group group = findGroupById(department, groupId);
        if (group == null) {
            return new ResponseEntity<>("Group not found with id: " + groupId, HttpStatus.NOT_FOUND);
        }

        group.setStudents(
                group.getStudents().stream()
                        .filter(student -> !student.getId().equals(studentId))
                        .toList());

        roleService.deleteRoleById(studentId);
        try {
            FirebaseAuth.getInstance().deleteUser(studentId);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete user from Firebase: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        sessionRepository.save(session);

        return new ResponseEntity<>("Student with id " + studentId + " successfully deleted.", HttpStatus.OK);
    }

    private Session findSessionById(String sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
    }

    private Department findDepartmentById(Session session, String departmentId) {
        return session.getDepartment().stream()
                .filter(department -> department.getDepartmentId().equals(departmentId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Department not found"));
    }

    private Group findGroupById(Department department, String groupId) {
        return department.getGroups().stream()
                .filter(group -> group.getGroupId().equals(groupId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Group not found"));
    }

    public void addStudentsFromExcel(MultipartFile file, String sessionId, String departmentId, String groupId)
            throws Exception {
        InputStream inputStream = file.getInputStream();
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        List<Student> students = new ArrayList<>();

        for (Row row : sheet) {
            if (row.getRowNum() == 0)
                continue;

            try {
                Student student = new Student();
                student.setName(getCellValueAsString(row.getCell(0)));
                student.setEmail(getCellValueAsString(row.getCell(1)));
                student.setCin(getCellValueAsString(row.getCell(2)));
                student.setGroup(getCellValueAsString(row.getCell(3)));

                AuthRequest userFirebase = new AuthRequest();
                userFirebase.setEmail(student.getEmail());
                userFirebase.setPassword(student.getCin());

                AuthResponse authResponse = firebaseService.createUser(userFirebase);

                if (authResponse.isError()) {
                    throw new RuntimeException("Error registering user in Firebase: " + authResponse.getMessage());
                }

                student.setId(authResponse.getUid());
                students.add(student);

                Role roleUser = new Role();
                roleUser.setIdUser(authResponse.getUid());
                roleUser.setRoleUser("Student");
                roleService.addRole(roleUser);

            } catch (Exception e) {
                throw new RuntimeException("Error processing row " + row.getRowNum() + ": " + e.getMessage(), e);
            }
        }

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        Department department = session.getDepartment().stream()
                .filter(d -> d.getDepartmentId().equals(departmentId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Group group = department.getGroups().stream()
                .filter(g -> g.getGroupId().equals(groupId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Group not found"));

        group.getStudents().addAll(students);
        sessionRepository.save(session);

        workbook.close();
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }
}
