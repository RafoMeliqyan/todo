package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    private int id;
    private String name;
    private Date createdDate;
    private String deadLine;
    private String status;
    private int userid;

    public Todo(String name, Date createdDate, String deadLine, String status) {
        this.name = name;
        this.createdDate = createdDate;
        this.deadLine = deadLine;
        this.status = status;
    }
}
