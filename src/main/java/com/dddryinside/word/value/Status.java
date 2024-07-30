package com.dddryinside.word.value;

import lombok.Getter;

@Getter
public enum Status {
    LEARNED("В изучении"),
    LEARN("Изучено");


    private final String name;

    Status(String name) {
        this.name = name;
    }
}
