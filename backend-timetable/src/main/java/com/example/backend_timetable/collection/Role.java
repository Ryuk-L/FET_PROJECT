package com.example.backend_timetable.collection;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.CrossOrigin;


@Data
@NoArgsConstructor
@AllArgsConstructor
@CrossOrigin(origins = "*")
@Document(collection = "role")

public class Role {
    @Id
    private String idUser;
    private String RoleUser;
    private List<String> sessionList=new ArrayList<>();
}