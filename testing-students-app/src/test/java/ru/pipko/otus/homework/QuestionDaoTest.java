package ru.pipko.otus.homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.pipko.otus.homework.config.CustomProperties;
import ru.pipko.otus.homework.dao.QuestionCsvDao;
import ru.pipko.otus.homework.dao.QuestionDao;
import ru.pipko.otus.homework.domain.Question;
import ru.pipko.otus.homework.exeptions.QuestionsDaoException;
import ru.pipko.otus.homework.service.CsvFileNameProvider;
import ru.pipko.otus.homework.service.CsvFileNameProviderImpl;

import java.util.Collections;
import java.util.List;

/**
 * Unit test for QuestionsDao
 */
public class QuestionDaoTest {

    private QuestionDao getQuestionDao(String fileName, String localeName){
        final CustomProperties customProperties = new CustomProperties();
        customProperties.setLocaleName(localeName);
        customProperties.setLocalizedFiles(Collections.singletonMap(localeName, fileName));
        CsvFileNameProvider provider = new CsvFileNameProviderImpl(customProperties);

        return new QuestionCsvDao(provider);
    }


    @DisplayName("Доступен ли файл с вопросами")
    @Test
    public void isFileWithQuestionsUnreachableOrEmpty() {
        final String fileName = "questions.csv";
        final String localeName = "ru-RU";
        final QuestionDao dao = getQuestionDao(fileName,localeName);

        List<Question> questionList = null;

        try {
            questionList = dao.getQuestions();
        } catch (QuestionsDaoException e) {
            e.printStackTrace();
        }

        Assertions.assertNotNull(questionList, "Questions list in file \"" + fileName + "\" is null");

        Assertions.assertFalse(questionList.isEmpty(), "Questions list in file \"" + fileName + "\" is empty");
    }

    @DisplayName("Вызывает ли исключение некорректный файл")
    @Test
    public void doesIncorrectFileProducesException() {
        final String fileName = "questions_without_flag.csv";
        final String localeName = "ru-RU";
        final QuestionDao dao = getQuestionDao(fileName,localeName);

        Assertions.assertThrows(QuestionsDaoException.class,
                () -> dao.getQuestions()
                , "File \"questions_without_flag.csv\" parsing should produce QuestionsDaoException exception");

    }


}
