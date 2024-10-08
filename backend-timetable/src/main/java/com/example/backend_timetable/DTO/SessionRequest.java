package com.example.backend_timetable.DTO;

import java.util.List;

import lombok.Data;


@Data
public class SessionRequest {
    private String year;
    private String universityName;
    private String timeBreakStart;
    private String timeBreakEnd;
    private String timeDayStart;
    private String timeDayEnd;
    private List<String> activeDays;
}
