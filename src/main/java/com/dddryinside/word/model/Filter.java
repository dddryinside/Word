package com.dddryinside.word.model;

import com.dddryinside.word.value.Language;
import com.dddryinside.word.value.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Filter {
    Status status;
    Language language;
    String query;
}
