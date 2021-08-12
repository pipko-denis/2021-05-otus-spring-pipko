package ru.pipko.otus.homework.plain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.pipko.otus.homework.dao.QuestionDao;
import ru.pipko.otus.homework.domain.Question;
import ru.pipko.otus.homework.exeptions.QuestionsDaoException;
import ru.pipko.otus.homework.service.CsvFileNameProvider;

import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Unit test for QuestionsDao
 */
@DisplayName("В QuestionCsvDaoTest")
@SpringBootTest
public class QuestionCsvDaoTest {

    @Autowired
    @Qualifier("questionCsvDao")
    private QuestionDao questionDao;


    @MockBean
    private CsvFileNameProvider csvFileNameProvider;


    @DisplayName(" заранее подготовленный файл корректно обрабатывается")
    @Test
    public void isFileWithQuestionsUnreachableOrEmpty() throws QuestionsDaoException {
        final String fileName = "questions.csv";
        when(csvFileNameProvider.getCsvFileName()).thenReturn(fileName);

        List<Question> questionList = questionDao.getQuestions();

        Assertions.assertNotNull(questionList, "Questions list in file \"" + fileName + "\" is null");

        Assertions.assertFalse(questionList.isEmpty(), "Questions list in file \"" + fileName + "\" is empty");
    }

    @DisplayName(" некорректный файл вызывает исключение ")
    @Test
    public void doesIncorrectFileProducesException() {
        when(csvFileNameProvider.getCsvFileName()).thenReturn("questions_without_flag.csv");

        Assertions.assertThrows(QuestionsDaoException.class,
                () -> questionDao.getQuestions()
                , "File \"questions_without_flag.csv\" parsing should produce QuestionsDaoException exception");

    }


}
