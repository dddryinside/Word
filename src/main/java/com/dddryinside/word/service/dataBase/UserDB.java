package com.dddryinside.word.service.dataBase;

import com.dddryinside.word.model.User;
import com.dddryinside.word.service.DataBaseAccess;

import java.sql.*;

public class UserDB {
    public static void saveUser(User user) throws Exception {
        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            isUserTableExists();

            String insertQuery = "INSERT INTO user (name, username, password, is_authorised) " +
                    "VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, user.getName());
                statement.setString(2, user.getUsername());
                statement.setString(3, user.getPassword());
                statement.setInt(4, user.isAuthorised() ? 1 : 0);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public static void updateUser(User user) throws Exception {
        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            String sql = "UPDATE user SET name = ?, username = ?, password = ?, is_authorised = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, user.getName());
                statement.setString(2, user.getUsername());
                statement.setString(3, user.getPassword());
                statement.setInt(4, user.isAuthorised() ? 1 : 0);
                statement.setInt(5, user.getId());

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new Exception();
                }
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public static User logIn(String username, String password) throws Exception {
        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            isUserTableExists();

            String insertQuery = "SELECT id, name, username, password FROM user WHERE username = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, username);
                statement.setString(2, password);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");

                        return new User(id, name, username, password);
                    } else {
                        throw new Exception("Пользователь не найден!");
                    }
                }
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public static void logOut() throws Exception {
        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            String sql = "UPDATE user SET is_authorised = 0";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public static User findAuthorisedUser() throws Exception {
        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            isUserTableExists();

            String insertQuery = "SELECT id, name, username, password FROM user WHERE is_authorised = 1";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");

                    return new User(id, name, username, password, true);
                }
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }

        return null;
    }

    public static boolean isUsernameAvailable(String username) {
        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            isUserTableExists();

            String insertQuery = "SELECT id FROM user WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();

                return !resultSet.next();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    private static void isUserTableExists() {
        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            if (!tableExists(connection)) {
                String createTableQuery = "CREATE TABLE IF NOT EXISTS user (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT," +
                        "username TEXT," +
                        "password TEXT," +
                        "is_authorised INTEGER)";
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(createTableQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean tableExists(Connection connection) throws SQLException {
        DatabaseMetaData metadata = connection.getMetaData();
        ResultSet resultSet = metadata.getTables(null, null, "user", null);
        return resultSet.next();
    }
}
