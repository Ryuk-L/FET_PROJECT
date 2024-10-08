package com.example.backend_timetable.DTO;

import lombok.Data;

import java.util.List;
@Data
public class SessionDTO {
    private String year;
    private String universityName;
    private String timeBreakStart;
    private String timeBreakEnd;
    private String timeDayStart;
    private String timeDayEnd;
    private List<String> activeDays;


}