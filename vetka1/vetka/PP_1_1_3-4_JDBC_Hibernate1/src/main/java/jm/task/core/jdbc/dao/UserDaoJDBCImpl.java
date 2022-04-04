package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String str = "CREATE TABLE IF NOT EXISTS users (id BIGINT NOT NULL primary key AUTO_INCREMENT, name CHAR(30),"
        + " lastName CHAR (30), age TINYINT UNSIGNED)";
        try {
            Statement statement = Util.getMySQLConnection().createStatement();
            statement.executeUpdate(str);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            Statement statement = Util.getMySQLConnection().createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement preparedStatement = Util.getMySQLConnection()
                    .prepareStatement("INSERT INTO users VALUES (id, ?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = Util.getMySQLConnection()
                        .prepareStatement("DELETE FROM users WHERE id = ?");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try {
            Statement statement = Util.getMySQLConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
            try {
                Statement statement = Util.getMySQLConnection().createStatement();
                statement.executeUpdate("DROP TABLE users");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
