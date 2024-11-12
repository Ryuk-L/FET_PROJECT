package com.example.backend_timetable.collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;




@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Teacher")
public class Teacher {
    @Id
    private String id;
    private String email;
    private String cin;
    private String teacherName;
    private boolean isValide=false;
    private List<Subjects> subjectsCanTeach;  
    private List<TimeSlotTeacher> timeSlots;        
}