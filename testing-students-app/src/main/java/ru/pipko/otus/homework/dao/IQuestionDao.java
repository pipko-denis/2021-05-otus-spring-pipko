package ru.pipko.otus.homework.dao;

import ru.pipko.otus.homework.domain.Question;

import java.util.Optional;

public interface IQuestionDao {

    Optional<Question> getNextQuestion();

    int getQuestionsCount();
}
