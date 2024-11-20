package com.example.backend_timetable.collection;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "rooms")
public class Room {
    @Id
    private String roomId;
    private String nameRoom;
    private int capacity;
    private String type;
}