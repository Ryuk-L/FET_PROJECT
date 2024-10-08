package com.example.backend_timetable.DTO;

import java.util.List;

import lombok.Data;

@Data
public class ActiveDaysRequest {
    private List<String> activeDays;
}
