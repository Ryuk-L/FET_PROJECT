package com.example.backend_timetable.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectWithRecurrence {
    private Subjects subject;
    private int recurrence;
}
