package com.dddryinside.service;

import com.dddryinside.model.Training;
import com.dddryinside.model.User;
import com.dddryinside.model.Word;
import com.dddryinside.page.MainPage;
import com.dddryinside.service.dataBase.TrainingDB;
import com.dddryinside.service.dataBase.UserDB;
import com.dddryinside.service.dataBase.WordDB;
import com.dddryinside.value.Avatar;
import com.dddryinside.value.Language;
import com.dddryinside.value.Status;
import com.dddryinside.model.Filter;
import com.dddryinside.page.LogInPage;
import com.dddryinside.value.TrainingType;

import java.util.ArrayList;
import java.util.List;

public class DataBaseAccess {
    public static final String DB_URL = "jdbc:sqlite:./word.db";
    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser (User user) {
        DataBaseAccess.user = user;
    }

    public static void saveUser(User user) {
        try {
            user.setAvatar(Avatar.AVATAR_DEFAULT);
            user.setLearningLanguage(Language.EN);
            user.setTrainingLength(10);
            UserDB.saveUser(user);

            DataBaseAccess.logIn(user.getUsername(), user.getPassword(), user.isAuthorised());
        } catch (Exception e) {
            PageManager.showNotification("Ошибка в работе базы данных!");
        }
    }

    public static void finishWork() {
        try {
            if (user != null) {
                UserDB.updateUser(user);
            }
        } catch (Exception e) {
            PageManager.showNotification("Ошибка в работе базы данных!");
        }
    }

    public static void logIn(String username, String password, boolean stayAuthorised) {
        try {
            user = UserDB.logIn(username, password);
            user.setAuthorised(stayAuthorised);
            PageManager.loadPage(new MainPage());
        } catch (Exception e) {
            PageManager.showNotification("Ошибка в работе базы данных!");
        }
    }

    public static void logOut() {
        try {
            UserDB.logOut();
            user = null;
            PageManager.loadPage(new LogInPage());
        } catch (Exception e) {
            PageManager.showNotification("Ошибка в работе базы данных!");
        }
    }

    public static void findAuthorisedUser() {
        try {
            user = UserDB.findAuthorisedUser();
        } catch (Exception e) {
            PageManager.showNotification("Ошибка в работе базы данных!");
        }

        if (user != null) {
            PageManager.loadPage(new MainPage());
        } else {
            PageManager.loadPage(new LogInPage());
        }
    }

    public static boolean isUsernameAvailable(String username) {
        return UserDB.isUsernameAvailable(username);
    }

    public static void saveWord(Word word) {
        try {
            WordDB.saveWord(word);
            user.setLearningLanguage(word.getLanguage());
        } catch (Exception e) {
            PageManager.showNotification("Ошибка в работе базы данных!");
        }

    }

    public static int getWordsAmount(TrainingType trainingType, Language language) {
        if (trainingType == TrainingType.LEARNING) {
            return WordDB.getWordsAmount(Status.LEARN, language);
        } else {
            return WordDB.getWordsAmount(Status.LEARNED, language);
        }
    }

    public static void deleteWord(Word word) {
        try {
            WordDB.deleteWord(word);
        } catch (Exception e) {
            PageManager.showNotification("Ошибка в работе базы данных!");
        }
    }

    public static Word getRandomWord(TrainingType trainingType, Language language) {
        return WordDB.getRandomWord(trainingType, language);
    }

    public static void updateWord(Word word) {
        try {
            WordDB.updateWord(word);
        } catch (Exception e) {
            PageManager.showNotification("Ошибка в работе базы данных!");
        }
    }

    public static void saveTrainingResult() {
        Training todaysTraining = null;
        try {
            todaysTraining = TrainingDB.wasThereTrainingToday();
        } catch (Exception e) {
            PageManager.showNotification("Ошибка в работе базы данных!");
        }

        if (todaysTraining == null) {
            try {
                TrainingDB.createNewTrainingToday();
            } catch (Exception e) {
                PageManager.showNotification("Ошибка в работе базы данных!");
            }
        } else {
            try {
                TrainingDB.incrementTodaysTraining(todaysTraining);
            } catch (Exception e) {
                PageManager.showNotification("Ошибка в работе базы данных!");
            }
        }
    }

    public static List<Training> getTrainingHistory() {
        List<Training> trainingHistory = new ArrayList<>();

        try {
            trainingHistory = TrainingDB.getTrainingHistory();
        } catch (Exception e) {
            PageManager.showNotification("Ошибка в работе базы данных!");
        }

        return trainingHistory;
    }

    public static int getVocabularyPagesAmount(Filter filter) {
        int pageSize = 20;
        int totalValue;

        if (filter.getQuery() == null) {
            totalValue = WordDB.getWordsAmount(filter.getStatus(), filter.getLanguage());
        } else {
            totalValue = WordDB.getWordsAmount(filter);
        }

        int totalPages = totalValue / pageSize;
        if (totalValue % pageSize != 0) {
            totalPages++;
        }

        return totalPages;
    }


    public static List<Word> getWords(Filter filter, int pageNumber) {
        List<Word> words;

        if (filter.getQuery() == null) {
            words = WordDB.getWords(filter.getStatus(), filter.getLanguage(), pageNumber);
        } else {
            words = WordDB.getWords(filter, pageNumber);
        }

        return words;
    }
}
