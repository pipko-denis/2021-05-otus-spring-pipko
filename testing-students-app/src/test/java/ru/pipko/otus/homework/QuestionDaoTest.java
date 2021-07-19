package ru.pipko.otus.homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.pipko.otus.homework.dao.QuestionCsvDao;
import ru.pipko.otus.homework.dao.QuestionDao;
import ru.pipko.otus.homework.domain.Question;
import ru.pipko.otus.homework.exeptions.QuestionsDaoException;

import java.util.List;

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
        String fileName = "questions_without_flag.csv";

        QuestionDao dao = new QuestionCsvDao(fileName);

        Assertions.assertThrows(QuestionsDaoException.class,
                () -> dao.getQuestions()
                , "File \"questions_without_flag.csv\" parsing should produce QuestionsDaoException exception");

    }


}
