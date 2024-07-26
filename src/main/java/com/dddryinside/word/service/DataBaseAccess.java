package com.dddryinside.word.service;

import com.dddryinside.word.model.User;
import com.dddryinside.word.page.LogInPage;
import com.dddryinside.word.page.MainPage;
import com.dddryinside.word.service.dataBase.UserDB;

public class DataBaseAccess {
    public static final String DB_URL = "jdbc:sqlite:./word.db";
    private static User user;

    public static User getUser() {
        return user;
    }

    public static void saveUser(User user) {
        try {
            UserDB.saveUser(user);
            DataBaseAccess.user = user;
            PageManager.loadPage(new MainPage());
            DataBaseAccess.logIn(user.getUsername(), user.getPassword(), user.isAuthorised());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateUser() {
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
}
