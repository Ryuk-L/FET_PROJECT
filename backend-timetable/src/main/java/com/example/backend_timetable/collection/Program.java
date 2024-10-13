package com.example.backend_timetable.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.ArrayList;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class Program {
    private String programName;
    private List<SubjectWithRecurrence> subjects = new ArrayList<>();
}