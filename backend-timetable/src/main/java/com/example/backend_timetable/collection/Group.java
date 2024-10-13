package com.example.backend_timetable.collection;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "group")
public class Group {
    @Id
    private String groupId;
    private String groupName;
    private int  numberGroups;
    private Program program;
}
