package com.dddryinside.word.model;

import com.dddryinside.word.value.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Word {
    private int id;
    private User user;
    private String word;
    private String translation;
    private Language language;
    private int status;

    public Word(User user, String word, String translation, Language language, int repNumber) {
        this.user = user;
        this.word = word;
        this.translation = translation;
        this.language = language;
        this.status = repNumber;
    }
}
