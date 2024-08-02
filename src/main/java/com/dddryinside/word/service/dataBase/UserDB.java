package com.dddryinside.word.service.dataBase;

import com.dddryinside.word.model.User;
import com.dddryinside.word.service.DataBaseAccess;
import com.dddryinside.word.value.Avatar;
import com.dddryinside.word.value.Language;

import java.sql.*;

public class UserDB {
    public static void saveUser(User user) throws Exception {
        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            isUserTableExists();

            String insertQuery = "INSERT INTO user (name, username, password, avatar, training_length, learning_language, is_authorised) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, user.getName());
                statement.setString(2, user.getUsername());
                statement.setString(3, user.getPassword());
                statement.setString(4, user.getAvatar().getFile());
                statement.setInt(5, user.getTrainingLength());
                statement.setString(6, user.getLearningLanguage().getShortName());
                statement.setInt(7, user.isAuthorised() ? 1 : 0);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    public static void updateUser(User user) throws Exception {
        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            String sql = "UPDATE user SET name = ?, username = ?, password = ?, avatar = ?, training_length = ?, learning_language = ?, is_authorised = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, user.getName());
                statement.setString(2, user.getUsername());
                statement.setString(3, user.getPassword());
                statement.setString(4, user.getAvatar().getFile());
                statement.setInt(5, user.getTrainingLength());
                statement.setString(6, user.getLearningLanguage().getShortName());
                statement.setInt(7, user.isAuthorised() ? 1 : 0);
                statement.setInt(8, user.getId());

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected == 0) {
                    System.out.println("No affected rows!");
                    throw new Exception();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    public static User logIn(String username, String password) throws Exception {
        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            isUserTableExists();

            String insertQuery = "SELECT * FROM user WHERE username = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, username);
                statement.setString(2, password);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String avatarFile = resultSet.getString("avatar");
                        int trainingLength = resultSet.getInt("training_length");
                        String learningLanguage = resultSet.getString("learning_language");
                        int isAuthorised  = resultSet.getInt("is_authorised");

                        User user = new User();
                        user.setId(id);
                        user.setName(name);
                        user.setUsername(username);
                        user.setPassword(password);
                        user.setAvatar(Avatar.getAvatarByFileName(avatarFile));
                        user.setTrainingLength(trainingLength);
                        user.setLearningLanguage(Language.getLanguageByShortName(learningLanguage));

                        if (isAuthorised == 1) {
                            user.setAuthorised(true);
                        } else {
                            user.setAuthorised(false);
                        }

                        return user;
                    } else {
                        throw new Exception("Пользователь не найден!");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    public static User findAuthorisedUser() throws Exception {
        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            isUserTableExists();

            String insertQuery = "SELECT * FROM user WHERE is_authorised = 1";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String avatarFile = resultSet.getString("avatar");
                    int trainingLength = resultSet.getInt("training_length");
                    String learningLanguage = resultSet.getString("learning_language");
                    int isAuthorised  = resultSet.getInt("is_authorised");

                    User user = new User();
                    user.setId(id);
                    user.setName(name);
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setAvatar(Avatar.getAvatarByFileName(avatarFile));
                    user.setTrainingLength(trainingLength);
                    user.setLearningLanguage(Language.getLanguageByShortName(learningLanguage));

                    if (isAuthorised == 1) {
                        user.setAuthorised(true);
                    } else {
                        user.setAuthorised(false);
                    }

                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
                        "avatar TEXT," +
                        "training_length INTEGER," +
                        "learning_language TEXT," +
                        "is_authorised INTEGER)";
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(createTableQuery);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static boolean tableExists(Connection connection) throws SQLException {
        DatabaseMetaData metadata = connection.getMetaData();
        ResultSet resultSet = metadata.getTables(null, null, "user", null);
        return resultSet.next();
    }
}
