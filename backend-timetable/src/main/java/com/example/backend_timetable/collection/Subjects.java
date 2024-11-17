package com.example.backend_timetable.collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Subject")
public class Subjects {
    @Id
    private String id;
    private String subjectName;
    private String duration; 
    private String type;
}
