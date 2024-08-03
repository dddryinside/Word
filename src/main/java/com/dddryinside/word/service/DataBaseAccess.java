package com.dddryinside.word.service;

import com.dddryinside.word.model.Filter;
import com.dddryinside.word.model.Training;
import com.dddryinside.word.model.User;
import com.dddryinside.word.model.Word;
import com.dddryinside.word.page.LogInPage;
import com.dddryinside.word.page.MainPage;
import com.dddryinside.word.service.dataBase.TrainingDB;
import com.dddryinside.word.service.dataBase.UserDB;
import com.dddryinside.word.service.dataBase.WordDB;
import com.dddryinside.word.value.Avatar;
import com.dddryinside.word.value.Language;
import com.dddryinside.word.value.Status;
import com.dddryinside.word.value.TrainingType;

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
            System.out.println(e.getMessage());
        }
    }

    public static void finishWork() {
        try {
            if (user != null) {
                UserDB.updateUser(user);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void logIn(String username, String password, boolean stayAuthorised) {
        try {
            user = UserDB.logIn(username, password);
            user.setAuthorised(stayAuthorised);
            PageManager.loadPage(new MainPage());
        } catch (Exception e) {
            PageManager.showNotification(e.getMessage());
        }
    }

    public static void logOut() {
        try {
            UserDB.logOut();
            user = null;
            PageManager.loadPage(new LogInPage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void findAuthorisedUser() {
        try {
            user = UserDB.findAuthorisedUser();
            if (user != null) {
                PageManager.loadPage(new MainPage());
            } else {
                PageManager.loadPage(new LogInPage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
        }
    }

    public static void saveTrainingResult() {
        Training todaysTraining = null;
        try {
            todaysTraining = TrainingDB.wasThereTrainingToday();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (todaysTraining == null) {
            try {
                TrainingDB.createNewTrainingToday();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                TrainingDB.incrementTodaysTraining(todaysTraining);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Training> getTrainingHistory() {
        List<Training> trainingHistory = new ArrayList<>();

        try {
            trainingHistory = TrainingDB.getTrainingHistory();
        } catch (Exception e) {
            e.printStackTrace();
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
