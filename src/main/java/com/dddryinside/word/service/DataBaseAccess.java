package com.dddryinside.word.service;

import com.dddryinside.word.model.User;
import com.dddryinside.word.model.Word;
import com.dddryinside.word.page.LogInPage;
import com.dddryinside.word.page.MainPage;
import com.dddryinside.word.service.dataBase.UserDB;
import com.dddryinside.word.service.dataBase.WordDB;
import com.dddryinside.word.value.Avatar;
import com.dddryinside.word.value.Language;
import com.dddryinside.word.value.TrainingType;

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
        WordDB.saveWord(word);
    }

    public static int getWordsAmount(TrainingType trainingType, Language language) {
        return WordDB.getWordsAmount(trainingType, language);
    }

    public static List<Word> getWords() {
        return WordDB.getWords();
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
}
