package ru.pipko.otus.homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.pipko.otus.homework.dao.QuestionDao;
import ru.pipko.otus.homework.dao.QuestionCsvDao;
import ru.pipko.otus.homework.domain.Question;
import ru.pipko.otus.homework.exeptions.QuestionsDaoException;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Unit test for QuestionsDao
 */
public class QuestionDaoTest {


    @Test
    public void isFileWithQuestionsUnreachableOrEmpty() {
        String fileName = "questions.csv";

        QuestionDao dao = new QuestionCsvDao(fileName);

        List<Question> questionList = null;

        try {
            questionList = dao.getQuestions();
        } catch (QuestionsDaoException e) {
            e.printStackTrace();
        }

        Assertions.assertNotNull(questionList, "Questions list in file \"" + fileName + "\" is null");

        Assertions.assertFalse(questionList.isEmpty(), "Questions list in file \"" + fileName + "\" is empty");
    }

    @Test
    public void doesIncorrectFileProducesException() {
        String fileName = "questions_second_line_error.csv";

        QuestionDao dao = new QuestionCsvDao(fileName);

        Assertions.assertThrows(QuestionsDaoException.class,
                () -> dao.getQuestions()
                , "File \"questions_second_line_error.csv\" parsing should produce QuestionsDaoException exception");

    }

}
