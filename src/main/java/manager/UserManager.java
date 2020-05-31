package manager;

import db.DBConnectionProvider;
import model.User;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserManager {
    private Connection connection;

    public UserManager() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }

    public void addUser(User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("Insert into user (name,surname,email,password) Values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurName());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());

        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();

        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            user.setId(id);
        }
    }

    public List<User> getAllUsers() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
        List<User> users = new LinkedList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setSurName(resultSet.getString("surname"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            users.add(user);
        }
        return users;
    }

    public void deleteUserById(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user WHERE id = ?");
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
    }

    public List<User> getUser(String email, String password) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSetEmail = statement.executeQuery("SELECT * FROM user WHERE email = ?");
        ResultSet resultSetPassword = statement.executeQuery("SELECT * FROM user WHERE password = ?");
        List<User> users = new LinkedList<>();
        while (resultSetEmail.next() && resultSetPassword.next()) {
            User user = new User();
            user.setEmail(resultSetEmail.getString("email"));
            user.setPassword(resultSetPassword.getString("password"));
            users.add(user);
        }
        return users;
    }
}
