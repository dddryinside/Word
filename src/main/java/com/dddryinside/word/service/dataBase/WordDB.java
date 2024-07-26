package com.dddryinside.word.service.dataBase;

import com.dddryinside.word.model.Word;
import com.dddryinside.word.service.DataBaseAccess;

import java.sql.*;

public class WordDB {
    public static void saveWord(Word word) {
        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            isWordsTableExists();

            String insertQuery = "INSERT INTO word (user_id, word, translation, language, repNumber) " +
                    "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setInt(1, word.getUser().getId());
                statement.setString(2, word.getWord());
                statement.setString(3, word.getTranslation());
                statement.setString(4, word.getLanguage().getShortName());
                statement.setInt(5, word.getRepNumber());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void isWordsTableExists() {
        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            if (!tableExists(connection)) {
                String createTableQuery = "CREATE TABLE IF NOT EXISTS word (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "user_id INTEGER," +
                        "word TEXT," +
                        "translation TEXT," +
                        "language TEXT," +
                        "repNumber INTEGER)";
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
        ResultSet resultSet = metadata.getTables(null, null, "word", null);
        return resultSet.next();
    }
}
