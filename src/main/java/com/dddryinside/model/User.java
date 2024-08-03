package com.dddryinside.model;

import com.dddryinside.value.Avatar;
import com.dddryinside.value.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String name;
    private String username;
    private String password;
    private Avatar avatar;
    private int trainingLength;
    private Language learningLanguage;
    public boolean authorised;

    @Override
    public String toString() {
        return "id = " + id +
                "\nname = " + name +
                "\nusername = " + username +
                "\npassword = " + password +
                "\navatar = " + avatar +
                "\ntraining_length = " + trainingLength +
                "\nlearning_language = " + learningLanguage +
                "\nauthorised = " + authorised;
    }
}
