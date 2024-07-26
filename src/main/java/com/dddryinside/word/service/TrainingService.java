package com.dddryinside.word.service;

import com.dddryinside.word.model.Word;
import com.dddryinside.word.page.TrainingPage;
import com.dddryinside.word.value.TrainingType;

import java.util.List;

public class TrainingService {
    private final TrainingType trainingType;
    private final List<Word> words ;
    private int iteration = 0;
    public TrainingService(TrainingType trainingType) {
        this.trainingType = trainingType;
        this.words = DataBaseAccess.getWords(10);
    }

    public void iterate() {
        iteration += 1;
        PageManager.loadPage(new TrainingPage(words.get(iteration - 1), trainingType));
    }
}
