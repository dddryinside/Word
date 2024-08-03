package com.dddryinside.value;

import lombok.Getter;

@Getter
public enum Language {
    EN("Английский", "en"),
    ES("Испанский", "es"),
    FR("Французский", "fr"),
    DE("Немецкий", "de"),
    BY("Белорусский", "by");


    private final String name;
    private final String shortName;

    Language(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    public static Language getLanguageByShortName(String shortName) {
        for (Language language : Language.values()) {
            if (language.getShortName().equals(shortName)) {
                return language;
            }
        }

        return null;
    }
}
