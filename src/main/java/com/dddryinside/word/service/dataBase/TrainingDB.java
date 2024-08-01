package com.dddryinside.word.service.dataBase;

import com.dddryinside.word.model.Training;
import com.dddryinside.word.model.TrainingIteration;
import com.dddryinside.word.model.Word;
import com.dddryinside.word.service.DataBaseAccess;
import com.dddryinside.word.value.Language;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TrainingDB {
    public static void createNewTrainingToday() throws Exception {
        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            isTrainingTableExists();

            String insertQuery = "INSERT INTO training (user_id, date, amount) " +
                    "VALUES (?, ?, 1)";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setInt(1, DataBaseAccess.getUser().getId());
                statement.setString(2, getTodayDateString());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    public static Training wasThereTrainingToday() throws Exception {
        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            isTrainingTableExists();

            String insertQuery = "SELECT * FROM training WHERE date = ?";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, getTodayDateString());
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int userId = resultSet.getInt("user_id");
                    String date = resultSet.getString("date");
                    int amount = resultSet.getInt("amount");

                    Training training = new Training();
                    training.setId(id);
                    training.setUserId(userId);
                    training.setDate(date);
                    training.setAmount(amount);

                    return training;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    public static void incrementTodaysTraining(Training training) throws Exception {
        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            String sql = "UPDATE training SET amount = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, training.getAmount() + 1);
                statement.setInt(2, training.getUserId());

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new Exception();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    public static List<Training> getTrainingHistory() throws Exception {
        List<Training> trainingHistory = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            isTrainingTableExists();

            String query = "SELECT * FROM training WHERE id = ? ORDER BY id DESC LIMIT 7";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, DataBaseAccess.getUser().getId());
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Training training = new Training();

                    training.setDate(resultSet.getString("date"));
                    training.setAmount(resultSet.getInt("amount"));

                    trainingHistory.add(training);
                }

                return trainingHistory;
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    private static void isTrainingTableExists() {
        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            if (!tableExists(connection)) {
                String createTableQuery = "CREATE TABLE IF NOT EXISTS training (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "user_id INTEGER," +
                        "date TEXT," +
                        "amount INTEGER)";
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
        ResultSet resultSet = metadata.getTables(null, null, "training", null);
        return resultSet.next();
    }

    private static String getTodayDateString() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");
        return today.format(formatter);
    }

}
