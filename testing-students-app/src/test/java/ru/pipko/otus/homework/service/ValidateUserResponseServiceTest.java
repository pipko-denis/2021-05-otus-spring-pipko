package ru.pipko.otus.homework.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import ru.pipko.otus.homework.config.CustomProperties;
import ru.pipko.otus.homework.dao.QuestionDao;
import ru.pipko.otus.homework.domain.Question;
import ru.pipko.otus.homework.events.PublisherForEvents;
import ru.pipko.otus.homework.exeptions.QuestionsDaoException;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = {ValidateUserResponseServiceImpl.class})
@DisplayName("В ValidateUserResponseServiceTest: ")
public class ValidateUserResponseServiceTest {

    @Configuration
    static class Config {

    }

    @Autowired
    private ValidateUserResponseService validateQuestionService;

    @MockBean
    private QuestionDao questionDao;

    @MockBean
    private CustomProperties customProperties;

    @MockBean
    private PublisherForEvents publisherForEvents;

    @MockBean
    private AskQuestionsService askQuestionsService;

    @DisplayName(" буквы \"abc\" - не корректный ответ пользователя")
    @Test
    public void isAbcIsNotValidUserResponse() throws QuestionsDaoException {

        List<Question> questionList = Collections.singletonList(new Question("question 1", new String[]{"111::true", "222::false", "333::false"}));
        given(questionDao.getQuestions()).willReturn(questionList);

        String userAnswer = "abc";

        Assertions.assertFalse(
                validateQuestionService.isUserResponseIsValid(questionDao.getQuestions().get(0), userAnswer)
                , "letters \""+userAnswer+"\""+"  - should be incorrect user response");

    }

    @DisplayName(" число \"1\" - корректный ответ пользователя")
    @Test
    public void isDigitOneIsCorrectAnswer() throws QuestionsDaoException {
        List<Question> questionList = Collections.singletonList(new Question("question 1", new String[]{"111::true", "222::false", "333::false"}));
        given(questionDao.getQuestions()).willReturn(questionList);

        String userAnswer = "1";

        Assertions.assertTrue(
                validateQuestionService.isUserResponseIsValid(questionDao.getQuestions().get(0), userAnswer)
                , "user answer \""+userAnswer+"\""+"  - should be an incorrect user response");

    }

    @DisplayName(" число \"4\" - не корректный ответ пользователя, т.к. на вопрос есть только один вариант ответа")
    @Test
    public void isDigitFourIsIncorrectAnswer() throws QuestionsDaoException {
        List<Question> questionList = Collections.singletonList(new Question("question 1", new String[]{"111::true", "222::false", "333::false"}));
        given(questionDao.getQuestions()).willReturn(questionList);

        String userAnswer = "4";

        Assertions.assertFalse(
                validateQuestionService.isUserResponseIsValid(questionDao.getQuestions().get(0), userAnswer)
                , "Number \""+userAnswer+"\""+"  - should be an incorrect user response");

    }


}
