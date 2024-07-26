package com.dddryinside.word.value;

import lombok.Getter;

@Getter
public enum TrainingType {
    LEARNING("Изучение слов"),
    REPETITION("Повторение слов");

    private final String name;

    TrainingType(String name) {
        this.name = name;
    }
}
