package ru.pipko.otus.homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.pipko.otus.homework.config.CsvCustomProperties;
import ru.pipko.otus.homework.config.LocaleCustomProperties;
import ru.pipko.otus.homework.dao.QuestionCsvDao;
import ru.pipko.otus.homework.dao.QuestionDao;
import ru.pipko.otus.homework.domain.Question;
import ru.pipko.otus.homework.exeptions.QuestionsDaoException;

import java.util.Collections;
import java.util.List;

/**
 * Unit test for QuestionsDao
 */
public class QuestionDaoTest {


    @DisplayName("Доступен ли файл с вопросами")
    @Test
    public void isFileWithQuestionsUnreachableOrEmpty() {
        final String fileName = "questions.csv";
        String localeName = "ru-RU";
        final LocaleCustomProperties localeCustomProperties = new LocaleCustomProperties();
        localeCustomProperties.setLocaleName(localeName);
        final CsvCustomProperties csvCustomProperties = new CsvCustomProperties(localeCustomProperties);
        csvCustomProperties.setLocalizedFiles(Collections.singletonMap(localeName, fileName));

        final QuestionDao dao = new QuestionCsvDao(csvCustomProperties);

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
        String localeName = "ru-RU";
        final LocaleCustomProperties localeCustomProperties = new LocaleCustomProperties();
        localeCustomProperties.setLocaleName(localeName);
        final CsvCustomProperties csvCustomProperties = new CsvCustomProperties(localeCustomProperties);
        csvCustomProperties.setLocalizedFiles(Collections.singletonMap(localeName, fileName));

        final QuestionDao dao = new QuestionCsvDao(csvCustomProperties);

        Assertions.assertThrows(QuestionsDaoException.class,
                () -> dao.getQuestions()
                , "File \"questions_without_flag.csv\" parsing should produce QuestionsDaoException exception");

    }


}
