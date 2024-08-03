package com.dddryinside.model;

import com.dddryinside.value.Language;
import com.dddryinside.value.Status;
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
