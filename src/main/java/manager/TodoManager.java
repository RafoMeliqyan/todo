package manager;

import db.DBConnectionProvider;
import model.Todo;
import status.TodoStatus;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TodoManager {
    private Connection connection;
    private UserManager userManager = new UserManager();

    public TodoManager() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public boolean create(Todo todo) {
        String sql = "INSERT INTO todo(title,deadline,status,user_id) VALUES(?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, todo.getTitle());
            if (todo.getDeadLine() != null) {
                statement.setString(2, sdf.format(todo.getDeadLine()));
            } else {
                statement.setString(2,null);
            }
            statement.setString(3, todo.getStatus().name());
            statement.setLong(4, todo.getUser().getId());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                todo.setId(rs.getLong(1));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Todo getById(long id) {
        String sql = "SELECT * FROM todo WHERE id = " + id;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return getTodoFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(long id, TodoStatus status) {
        String sql = "UPDATE todo SET status = '" + status.name() + "' WHERE id = " + id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM todo WHERE id = " + id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Todo getTodoFromResultSet(ResultSet resultSet) {
        try {
            try {
                return Todo.builder()
                        .id(resultSet.getLong(1))
                        .title(resultSet.getString(2))
                        .deadLine(resultSet.getString(3) == null ? null : sdf.parse(resultSet.getString(3)))
                        .status(TodoStatus.valueOf(resultSet.getString(4)))
                        .user(userManager.getById(resultSet.getLong(5)))
                        .createdDate(sdf.parse(resultSet.getString(6)))
                        .build();
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Todo> getAllTodosByUser(long user_id) {
        List<Todo> todos = new ArrayList<>();
        String sql = "SELECT * FROM todo WHERE user_id = " + user_id;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                todos.add(getTodoFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }


    public List<Todo> getAllTodosBtUserAndStatus(long user_id, TodoStatus status) {
        List<Todo> todos = new ArrayList<>();
        String sql = "SELECT * FROM todo WHERE user_id = ? AND status = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, user_id);
            statement.setString(2, status.name());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                todos.add(getTodoFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }
}
