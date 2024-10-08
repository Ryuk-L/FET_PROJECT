package com.example.backend_timetable.DTO;

import lombok.Data;

@Data
public class TimeBreakRequest {
    private String timeBreakEnd;
    private String timeBreakStart;
}
