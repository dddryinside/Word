package com.dddryinside.word.model;

import com.dddryinside.word.value.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingIteration {
    Word word;
    TrainingType trainingType;
    List<String> options;
    int size;
    int iteration;
}
