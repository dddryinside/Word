package com.dddryinside.word.service.dataBase;

import com.dddryinside.word.model.Word;
import com.dddryinside.word.service.DataBaseAccess;
import com.dddryinside.word.value.Language;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WordDB {
    public static void saveWord(Word word) {
        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            isWordsTableExists();

            String insertQuery = "INSERT INTO word (user_id, word, translation, language, rep_number) " +
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

    public static List<Word> getWords(int limit) {
        List<Word> words = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            isWordsTableExists();

            String query = "SELECT * FROM word WHERE user_id = ? AND rep_number < 10 ORDER BY rep_number ASC LIMIT ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, DataBaseAccess.getUser().getId());
                statement.setInt(2, limit);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Word word = new Word();

                    word.setId(resultSet.getInt("id"));
                    word.setUser(DataBaseAccess.getUser());
                    word.setWord(resultSet.getString("word"));
                    word.setTranslation(resultSet.getString("translation"));
                    word.setLanguage(Language.getLanguageByShortName(resultSet.getString("language")));
                    word.setRepNumber(resultSet.getInt("rep_number"));

                    words.add(word);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return words;
    }

    public static List<Word> getWords() {
        List<Word> words = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            isWordsTableExists();

            String query = "SELECT * FROM word WHERE user_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, DataBaseAccess.getUser().getId());
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Word word = new Word();

                    word.setId(resultSet.getInt("id"));
                    word.setUser(DataBaseAccess.getUser());
                    word.setWord(resultSet.getString("word"));
                    word.setTranslation(resultSet.getString("translation"));
                    word.setLanguage(Language.getLanguageByShortName(resultSet.getString("language")));
                    word.setRepNumber(resultSet.getInt("rep_number"));

                    words.add(word);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return words;
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
                        "rep_number INTEGER)";
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
