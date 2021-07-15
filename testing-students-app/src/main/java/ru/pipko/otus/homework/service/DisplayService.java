package ru.pipko.otus.homework.service;

import ru.pipko.otus.homework.domain.Answer;
import ru.pipko.otus.homework.domain.Interview;

import java.util.List;

public interface DisplayService {

    void displayAnswers(List<Answer> answers);

    void displayInterviewResults(Interview interview);

}
