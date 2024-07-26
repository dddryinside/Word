package com.dddryinside.word.model;

import com.dddryinside.word.value.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Word {
    private int id;
    private final User user;
    private final String word;
    private final String translation;
    private final Language language;
    private final int repNumber;

    public Word(User user, String word, String translation, Language language, int repNumber) {
        this.user = user;
        this.word = word;
        this.translation = translation;
        this.language = language;
        this.repNumber = repNumber;
    }
}
