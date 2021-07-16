package ru.pipko.otus.homework;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.pipko.otus.homework.domain.Question;
import ru.pipko.otus.homework.service.ValidateUserResponseService;
import ru.pipko.otus.homework.service.ValidateUserResponseServiceImpl;


public class ValidateUserResponseServiceTest {

    private final ValidateUserResponseService validateQuestionService = new ValidateUserResponseServiceImpl();

    private final Question questionWithThreeAnswers  = new Question("question 1", new String[]{"111::true", "222::false", "333::false"});



    @Test
    public void isValidationQuestionServiseWorkingCorrect() {
        String letters = "abc";

        Assertions.assertFalse(
                this.validateQuestionService.isUserResponseIsValid(this.questionWithThreeAnswers, letters)
                , "letters \""+letters+"\""+"  - should be incorrect user response");

        Assertions.assertTrue(
                this.validateQuestionService.isUserResponseIsValid(this.questionWithThreeAnswers, "1")
                , "letters \""+letters+"\""+"  - should be incorrect user response");

        Assertions.assertFalse(
                this.validateQuestionService.isUserResponseIsValid(this.questionWithThreeAnswers, "4")
                , "Number \"4\""+"  - should be incorrect user response");

    }


}
