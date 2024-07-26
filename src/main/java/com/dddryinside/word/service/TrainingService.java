package com.dddryinside.word.service;

import com.dddryinside.word.model.Training;
import com.dddryinside.word.model.Word;
import com.dddryinside.word.page.TrainingPage;
import com.dddryinside.word.value.TrainingType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TrainingService {
    private static TrainingType trainingType;
    private static List<Word> words;
    private static int iteration = 0;

    public static void initializeTraining (TrainingType trainingType) {
        TrainingService.trainingType = trainingType;
        TrainingService.words = DataBaseAccess.getWords(10);
        Collections.shuffle(words);
    }

    public static void iterate() {
        Training training = new Training();

        training.setWord(words.get(iteration));
        training.setTrainingType(trainingType);
        training.setOptions(getOptions());
        training.setSize(words.size());
        training.setIteration(iteration);

        iteration += 1;

        PageManager.loadPage(new TrainingPage(training));
    }

    private static List<String> getOptions() {
        List<String> options = new ArrayList<>();
        while (options.size() != 3) {
            int randomIndex = ThreadLocalRandom.current().nextInt(words.size());
            String randomOption = words.get(randomIndex).getTranslation();
            if (!randomOption.equals(words.get(iteration).getTranslation())) {

                boolean alreadyExist = false;
                for (String option : options) {
                    if (option.equals(randomOption)) {
                        alreadyExist = true;

                        break;
                    }
                }

                if (!alreadyExist) {
                    options.add(randomOption);
                }
            }
        }
        options.add(words.get(iteration).getTranslation());
        Collections.shuffle(options);
        return options;
    }

    public static List<Word> getWords() {
        return words;
    }
}
