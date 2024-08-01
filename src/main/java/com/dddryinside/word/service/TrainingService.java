package com.dddryinside.word.service;

import com.dddryinside.word.model.TrainingIteration;
import com.dddryinside.word.model.Word;
import com.dddryinside.word.page.TrainingPage;
import com.dddryinside.word.value.Language;
import com.dddryinside.word.value.TrainingType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrainingService {
    private static TrainingType trainingType;
    private static Language language;
    private static int iteration = 0;
    private static Word currentWord;
    private static List<Word> learnedWords = new ArrayList<>();

    public static void startTraining(TrainingType trainingType, Language language) {
        TrainingService.trainingType = trainingType;
        TrainingService.language = language;

        iterate();
    }

    public static void stopTraining() {
        for (Word word : learnedWords) {
            word.setStatus(1);
            DataBaseAccess.updateWord(word);
        }

        iteration = 0;
    }

    public static void iterate() {
        currentWord = DataBaseAccess.getRandomWord(trainingType, language);
        TrainingIteration training = new TrainingIteration();

        training.setWord(currentWord);
        training.setTrainingType(trainingType);
        training.setOptions(getOptions());
        training.setSize(SettingAccess.getTrainingLength());
        training.setIteration(iteration);

        iteration += 1;

        PageManager.loadPage(new TrainingPage(training));
    }

    private static List<String> getOptions() {
        List<String> options = new ArrayList<>();
        while (options.size() != 3) {
            Word randomWord = DataBaseAccess.getRandomWord(trainingType, language);

            boolean alreadyExist = false;
            for (String option : options) {
                if (option.equals(randomWord.getTranslation())) {
                    alreadyExist = true;
                    break;
                }
            }

            if (!alreadyExist && !randomWord.getTranslation().equals(currentWord.getTranslation())) {
                options.add(randomWord.getTranslation());
            }
        }

        options.add(currentWord.getTranslation());
        Collections.shuffle(options);
        return options;
    }

    public static void addLearnedWord(Word word) {
        learnedWords.add(word);
    }

    public static void removeLearnedWord(Word word) {
        learnedWords.removeIf(learnedWord -> learnedWord.getId() == word.getId());
    }

    public static boolean isWordLeaned(Word word) {
        for (Word learnedWord : learnedWords) {
            if (learnedWord.getId() == word.getId()) {
                return true;
            }
        }

        return false;
    }
}
