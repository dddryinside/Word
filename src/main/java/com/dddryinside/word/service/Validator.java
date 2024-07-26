package com.dddryinside.word.service;

public class Validator {
    public static boolean isNameValid(String name) {
        if (name == null || name.trim().isEmpty()) {
            PageManager.showNotification("Кажется, вы забыли ввести имя!");
            return false;
        } else {
            return true;
        }
    }

    public static boolean isUsernameValid(String username) {
        if (username == null || username.trim().isEmpty()) {
            PageManager.showNotification("Кажется, вы забыли ввести username!");
            return false;
        } else if (!DataBaseAccess.isUsernameAvailable(username)) {
            PageManager.showNotification("Это username уже занято, нужно придумать другое!");
            return false;
        } else {
            return true;
        }
    }

    public static boolean isPasswordValid(String password) {
        if (password == null || password.trim().isEmpty()) {
            PageManager.showNotification("Кажется, вы забыли ввести username!");
            return false;
        } else if (password.length() < 8) {
            PageManager.showNotification("Пароль не должен быть короче 8 символов!");
            return false;
        } else {
            return true;
        }
    }
}
