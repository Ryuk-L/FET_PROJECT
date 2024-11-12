package com.example.backend_timetable.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlotTeacher {
    private String day;         
    private String startTime;    
    private String endTime;      
}