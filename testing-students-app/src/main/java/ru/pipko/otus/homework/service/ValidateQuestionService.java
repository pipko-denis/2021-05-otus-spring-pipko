package ru.pipko.otus.homework.service;

import ru.pipko.otus.homework.domain.Question;
import ru.pipko.otus.homework.exeptions.ValidateQuestionException;

import java.util.List;

public interface ValidateQuestionService {

    void validateQuestionsList(List<Question> questions) throws ValidateQuestionException;

    /*
        public static DisplayQuestionException checkQuestion(Question question, int index) throws DisplayQuestionException {
        if (question == null) {
            return new DisplayQuestionException("Question #"+index+" is null");
        }

        if (question.getText() == null) {
            return new DisplayQuestionException("Question's #"+index+" text is null");
        }

        return null;
    }
    */

}
