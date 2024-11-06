package com.example.backend_timetable.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlotTeacher {
    private String day;          // e.g., Monday, Tuesday
    private String startTime;    // e.g., "08:00"
    private String endTime;      // e.g., "10:00"
}