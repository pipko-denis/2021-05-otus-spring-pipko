package ru.pipko.otus.homework.dao;

import ru.pipko.otus.homework.domain.Question;
import ru.pipko.otus.homework.exeptions.QuestionsDaoException;

import java.io.IOException;
import java.util.List;

public interface QuestionDao {

    List<Question> getQuestions() throws QuestionsDaoException;

}
