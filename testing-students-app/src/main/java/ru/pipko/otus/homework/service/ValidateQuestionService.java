package ru.pipko.otus.homework.service;

import ru.pipko.otus.homework.domain.Question;
import ru.pipko.otus.homework.exeptions.ValidateQuestionException;

import java.util.List;

public interface ValidateQuestionService {

    void validateQuestionsList(List<Question> questions) throws ValidateQuestionException;

}
