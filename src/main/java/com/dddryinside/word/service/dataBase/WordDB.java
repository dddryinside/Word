package com.dddryinside.word.service.dataBase;

import com.dddryinside.word.model.Filter;
import com.dddryinside.word.model.Word;
import com.dddryinside.word.service.DataBaseAccess;
import com.dddryinside.word.value.Language;
import com.dddryinside.word.value.Status;
import com.dddryinside.word.value.TrainingType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WordDB {
    public static void saveWord(Word word) throws Exception {
        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            isWordsTableExists();

            String insertQuery = "INSERT INTO word (user_id, word, translation, language, status) " +
                    "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setInt(1, word.getUser().getId());
                statement.setString(2, word.getWord());
                statement.setString(3, word.getTranslation());
                statement.setString(4, word.getLanguage().getShortName());
                statement.setInt(5, word.getStatus());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public static void updateWord(Word word) throws Exception {
        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            isWordsTableExists();

            String updateQuery = "UPDATE word SET word = ?, translation = ?, language = ?, status = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                statement.setString(1, word.getWord());
                statement.setString(2, word.getTranslation());
                statement.setString(3, word.getLanguage().getShortName());
                statement.setInt(4, word.getStatus());
                statement.setInt(5, word.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new Exception("DataBase error in updateWord()");
        }
    }

    public static void deleteWord(Word word) throws Exception {
        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            isWordsTableExists();

                String updateQuery = "DELETE FROM word WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                statement.setInt(1, word.getId());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public static int getWordsAmount(Status status, Language language) {
        int tableSize = 0;

        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            isWordsTableExists();

            String query = "SELECT COUNT(*) AS table_size FROM word WHERE user_id = ? AND status = ? AND language = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, DataBaseAccess.getUser().getId());

                if (status == Status.LEARNED) {
                    statement.setInt(2, 1);
                } else {
                    statement.setInt(2, 0);
                }

                statement.setString(3, language.getShortName());
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    tableSize = resultSet.getInt("table_size");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tableSize;
    }

    public static int getWordsAmount(Filter filter) {
        int tableSize = 0;

        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            isWordsTableExists();

            String query = "SELECT COUNT(*) AS table_size FROM word WHERE user_id = ? AND status = ? AND language = ? AND (word = ? OR translation = ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, DataBaseAccess.getUser().getId());

                if (filter.getStatus() == Status.LEARNED) {
                    statement.setInt(2, 1);
                } else {
                    statement.setInt(2, 0);
                }

                statement.setString(3, filter.getLanguage().getShortName());
                statement.setString(4, filter.getQuery());
                statement.setString(5, filter.getQuery());
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    tableSize = resultSet.getInt("table_size");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tableSize;
    }

    public static Word getRandomWord(TrainingType trainingType, Language language) {
        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            isWordsTableExists();

            int status = 0;
            if (trainingType == TrainingType.REPETITION) {
                status = 1;
            }

            String query = "SELECT * FROM word WHERE user_id = ? AND status = ? AND language = ? ORDER BY RANDOM() LIMIT 1";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, DataBaseAccess.getUser().getId());
                statement.setInt(2, status);
                statement.setString(3, language.getShortName());
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    Word word = new Word();

                    word.setId(resultSet.getInt("id"));
                    word.setUser(DataBaseAccess.getUser());
                    word.setWord(resultSet.getString("word"));
                    word.setTranslation(resultSet.getString("translation"));
                    word.setLanguage(Language.getLanguageByShortName(resultSet.getString("language")));
                    word.setStatus(resultSet.getInt("status"));

                    return word;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static List<Word> getWords(Status status, Language language, int pageNumber) {
        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            isWordsTableExists();

            int pageSize = 20;

            String insertQuery = "SELECT * FROM word WHERE user_id = ? AND status = ? AND language = ? ORDER BY id DESC LIMIT ? OFFSET ?";
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setInt(1, DataBaseAccess.getUser().getId());

            if (status == Status.LEARN) {
                statement.setInt(2, 0);
            } else {
                statement.setInt(2, 1);
            }

            statement.setString(3, language.getShortName());
            statement.setInt(4, pageSize);
            statement.setInt(5, (pageNumber - 1) * pageSize);
            ResultSet resultSet = statement.executeQuery();

            List<Word> words = new ArrayList<>();
            while (resultSet.next()) {
                words.add(getWordObject(resultSet));
            }

            return words;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Word> getWords(Filter filter, int pageNumber) {
        try (Connection connection = DriverManager.getConnection(DataBaseAccess.DB_URL)) {
            isWordsTableExists();

            int pageSize = 20;

            String insertQuery = "SELECT * FROM word WHERE user_id = ? AND status = ? AND language = ? AND (word = ? OR translation = ?) ORDER BY id DESC LIMIT ? OFFSET ?";
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setInt(1, DataBaseAccess.getUser().getId());

            if (filter.getStatus() == Status.LEARN) {
                statement.setInt(2, 0);
            } else {
                statement.setInt(2, 1);
            }

            statement.setString(3, filter.getLanguage().getShortName());

            statement.setString(4, filter.getQuery());
            statement.setString(5, filter.getQuery());

            statement.setInt(6, pageSize);
            statement.setInt(7, (pageNumber - 1) * pageSize);
            ResultSet resultSet = statement.executeQuery();

            List<Word> words = new ArrayList<>();
            while (resultSet.next()) {
                words.add(getWordObject(resultSet));
            }

            return words;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Word getWordObject(ResultSet resultSet) throws SQLException {
        Word word = new Word();

        word.setId(resultSet.getInt("id"));
        word.setUser(DataBaseAccess.getUser());
        word.setWord(resultSet.getString("word"));
        word.setTranslation(resultSet.getString("translation"));
        word.setLanguage(Language.getLanguageByShortName(resultSet.getString("language")));
        word.setStatus(resultSet.getInt("status"));

        return word;
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
                        "status INTEGER)";
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
