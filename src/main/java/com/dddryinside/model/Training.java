package com.dddryinside.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Training {
    private int id;
    private int userId;
    private String date;
    private int amount;
}
