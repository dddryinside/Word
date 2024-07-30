package com.dddryinside.word.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public final class SettingAccess {
    private static final String SETTINGS_FILE = "app.properties";
    private static final Properties settings;



    private static final String TRAINING_LENGTH = "training_length";


    static {
        settings = new Properties();
        try {
            settings.load(new FileInputStream(SETTINGS_FILE));
        } catch (IOException e) {
            settings.setProperty(TRAINING_LENGTH, String.valueOf(10));
            saveSettings();
        }
    }

    public static Integer getTrainingLength() {
        return Integer.parseInt(settings.getProperty(TRAINING_LENGTH));
    }

    public static void setTrainingLength(int trainingLength) {
        settings.setProperty(TRAINING_LENGTH, String.valueOf(trainingLength));
        saveSettings();
    }

    private static void saveSettings() {
        try {
            File settingsFile = new File(SETTINGS_FILE);
            if (!settingsFile.exists()) {
                settingsFile.createNewFile();
            }
            settings.store(new FileOutputStream(settingsFile), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
