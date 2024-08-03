package com.dddryinside.value;

import lombok.Getter;

@Getter
public enum Avatar {
    AVATAR_DEFAULT("avatar_default.png"),

    AVATAR_1("avatar-men-1.png"),
    AVATAR_2("avatar-men-2.png"),
    AVATAR_3("avatar-men-3.png"),
    AVATAR_4("avatar-men-4.png"),

    AVATAR_5("avatar-woman-1.png"),
    AVATAR_6("avatar-woman-2.png"),
    AVATAR_7("avatar-woman-3.png"),
    AVATAR_8("avatar-woman-4.png");

    private final String file;

    Avatar(String file) {
        this.file = file;
    }

    public static Avatar getAvatarByFileName(String fileName) {
        for (Avatar avatar : Avatar.values()) {
            if (avatar.getFile().equals(fileName)) {
                return avatar;
            }
        }
        return AVATAR_DEFAULT;
    }
}
