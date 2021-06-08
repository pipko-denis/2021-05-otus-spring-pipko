package ru.pipko.otus.homework.service;

import ru.pipko.otus.homework.domain.Question;

import java.util.List;

public interface IQuestionExtractorService {

    List<Question> getQuestions();

}
