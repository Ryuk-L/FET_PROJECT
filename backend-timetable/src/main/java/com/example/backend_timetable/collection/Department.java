package com.example.backend_timetable.collection;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "department")
public class Department {
    @Id
    private String departmentId;
    @Indexed(unique = true)
    private String departmentName;
    private List<Group> groups=new ArrayList<>();
    
}
