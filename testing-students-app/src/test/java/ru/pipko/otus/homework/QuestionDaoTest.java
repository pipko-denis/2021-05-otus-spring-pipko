package ru.pipko.otus.homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.pipko.otus.homework.config.CustomProperties;
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
        final String fileName = "questions.csv";
        final CustomProperties customProperties = new CustomProperties();
        customProperties.setCsvFileName(fileName);

        final QuestionDao dao = new QuestionCsvDao(customProperties);

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
        final String fileName = "questions_without_flag.csv";
        final CustomProperties customProperties = new CustomProperties();
        customProperties.setCsvFileName(fileName);

        final QuestionDao dao = new QuestionCsvDao(customProperties);

        Assertions.assertThrows(QuestionsDaoException.class,
                () -> dao.getQuestions()
                , "File \"questions_without_flag.csv\" parsing should produce QuestionsDaoException exception");

    }


}
