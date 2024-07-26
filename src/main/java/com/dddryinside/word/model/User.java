package com.dddryinside.word.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
    private int id;
    private final String name;
    private final String username;
    private final String password;
    public boolean authorised;

    public User (int id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public User (String name, String username, String password, boolean stayAuthorised) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.authorised = stayAuthorised;
    }

    public void setAuthorised(boolean authorised) {
        this.authorised = authorised;
    }

    @Override
    public String toString() {
        return "id = " + id +
                "\nname = " + name +
                "\nusername = " +
                "\npassword = " + password +
                "\nis_authorised = " + authorised;
    }
}
