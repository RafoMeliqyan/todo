package manager;

import model.Todo;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TodoManager {
    private static Connection connection;

    public static List<Todo> myList() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM todo");
        List<Todo> todos = new LinkedList<>();
        while (resultSet.next()) {
            Todo todo = new Todo();
            todo.setId(resultSet.getInt("id"));
            todo.setName(resultSet.getString("name"));
            todo.setCreatedDate(resultSet.getDate("createdData"));
            todo.setDeadLine(resultSet.getString("deadline"));
            todo.setStatus(resultSet.getString("status"));
            todos.add(todo);
        }
        return todos;
    }

    public static List<Todo> myInProgressList() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM todo WHERE status = IN_PROGRESS");
        List<Todo> todos = new LinkedList<>();
        while (resultSet.next()) {
            Todo todo = new Todo();
            todo.setId(resultSet.getInt("id"));
            todo.setName(resultSet.getString("name"));
            todo.setCreatedDate(resultSet.getDate("createdData"));
            todo.setDeadLine(resultSet.getString("deadline"));
            todo.setStatus(resultSet.getString("status"));
            todos.add(todo);
        }
        return todos;
    }

    public static List<Todo> myFinishedList() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM todo WHERE status = FINISHED");
        List<Todo> todos = new LinkedList<>();
        while (resultSet.next()) {
            Todo todo = new Todo();
            todo.setId(resultSet.getInt("id"));
            todo.setName(resultSet.getString("name"));
            todo.setCreatedDate(resultSet.getDate("createdData"));
            todo.setDeadLine(resultSet.getString("deadline"));
            todo.setStatus(resultSet.getString("status"));
            todos.add(todo);
        }
        return todos;
    }

    public static void addTodo(Todo todo) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("Insert into todo (id,name,deadline,status,user_id)",Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1,todo.getName());
        preparedStatement.setString(2,todo.getDeadLine());
        preparedStatement.setString(3,todo.getStatus());
        preparedStatement.setInt(4,todo.getUserid());

        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();

        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            todo.setId(id);
        }
    }

    public static void deleteTodoById(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM todo WHERE id = ?");
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
    }

    public static void changeTodoStatus(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM todo WHERE id = ?");
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
    }

    public static void setConnection(Connection connection) {
        TodoManager.connection = connection;
    }
}
