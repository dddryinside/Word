package com.dddryinside.word.value;

import lombok.Getter;

@Getter
public enum Status {
    LEARNED("Изучено"),
    LEARN("В изучении");


    private final String name;

    Status(String name) {
        this.name = name;
    }
}
