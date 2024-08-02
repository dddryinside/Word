package com.dddryinside.word.service;

import com.dddryinside.word.value.Language;

public class Validator {
    public static boolean isNameValid(String name) {
        if (name == null || name.trim().isEmpty()) {
            PageManager.showNotification("Кажется, вы забыли ввести имя!");
            return false;
        } else {
            if (name.length() > 15) {
                PageManager.showNotification("Сократите поле имени и фамилии до 15 символов!");
                return false;
            } else {
                return true;
            }
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
            PageManager.showNotification("Кажется, вы забыли ввести пароль!");
            return false;
        } else if (password.length() < 8) {
            PageManager.showNotification("Пароль не должен быть короче 8 символов!");
            return false;
        } else {
            return true;
        }
    }

    public static boolean isWordValid(String word) {
        if (word == null || word.trim().isEmpty()) {
            PageManager.showNotification("Кажется, вы забыли ввести слово!");
            return false;
        } else {
            return true;
        }
    }

    public static boolean isTranslationValid(String translation) {
        if (translation == null || translation.trim().isEmpty()) {
            PageManager.showNotification("Кажется, вы забыли ввести перевод!");
            return false;
        } else {
            return true;
        }
    }

    public static boolean isLanguageValid(Language language) {
        if (language == null) {
            PageManager.showNotification("Кажется, вы забыли выбрать язык!");
            return false;
        } else {
            return true;
        }
    }
}
