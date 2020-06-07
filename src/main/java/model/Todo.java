package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import status.TodoStatus;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo {

    private long id;
    private String title;
    private Date deadLine;
    private TodoStatus status;
    private User user;
    private Date createdDate;

    public Todo(String name, Date createdDate, Date deadLine, TodoStatus status) {
        this.title = name;
        this.createdDate = createdDate;
        this.deadLine = deadLine;
        this.status = status;
    }
}
