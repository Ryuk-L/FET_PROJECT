package com.example.backend_timetable.collection;



import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "group")
public class Group {
    @Id
    private String groupId;
    @Indexed(unique = true)
    private String groupName;
    private int  numberGroups;
    private Program program;
    private List<Student> students=new ArrayList<>();
}
