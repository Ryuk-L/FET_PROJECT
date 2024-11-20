package com.example.backend_timetable.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sessions")
public class Session {
    @Id
    private String sessionId;
    private String year;
    private String universityName;
    private String timeBreakStart;
    private String timeBreakEnd;
    private String timeDayStart;
    private String timeDayEnd;
    private List<String> activeDays;
    private List<Room> rooms=new ArrayList<>();
    private List<Subjects> subjects = new ArrayList<>(); 
    private List<Department> department = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();


}