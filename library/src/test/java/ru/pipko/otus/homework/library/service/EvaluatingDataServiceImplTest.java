package ru.pipko.otus.homework.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.*;
import ru.pipko.otus.homework.library.exceptions.EvaluatingException;

import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest
@DisplayName("Сервис EvaluatingDataServiceImpl должен:")
class EvaluatingDataServiceImplTest {

    public static final String EXCEPTION_MESSAGE = "Exception message";
    public static final String SOME_ENTITY_NAME = "Some entity name";

    @Autowired
    private EvaluatingDataService evaluatingService;

    @Configuration
    static class Config {
        @Bean
        public EvaluatingDataService getEvalService(){
            return new EvaluatingDataServiceImpl();
        }
    }


    @Test
    @DisplayName("при проверке является ли числом переданный параметр, если передать пустой текст,null или буквы, выбрасывать исключение")
    void checkOnDigitsShouldProduceExceptionOnBlankTextOrLetters() {
        assertThatCode( () -> evaluatingService.throwExceptionIfNotOnlyDigitsInText(null, EXCEPTION_MESSAGE)).isInstanceOf(EvaluatingException.class);
        assertThatCode( () -> evaluatingService.throwExceptionIfNotOnlyDigitsInText("", EXCEPTION_MESSAGE)).isInstanceOf(EvaluatingException.class);
        assertThatCode( () -> evaluatingService.throwExceptionIfNotOnlyDigitsInText("sdf", EXCEPTION_MESSAGE)).isInstanceOf(EvaluatingException.class);
    }

    @Test
    @DisplayName("при проверке текста, если передать пустой текст или null, выбрасывать исключение")
    void checkOnTextNotBlankShouldProduceExceptionOnBlankText() {
        assertThatCode( () -> evaluatingService.isTextNotNullAndNotBlank(null, EXCEPTION_MESSAGE)).isInstanceOf(EvaluatingException.class);
        assertThatCode( () -> evaluatingService.isTextNotNullAndNotBlank("", EXCEPTION_MESSAGE)).isInstanceOf(EvaluatingException.class);
    }

    @Test
    @DisplayName("при проверке массива на \"состоят ли все строковые элементы из чисел\", при не корректных данных, выбрасывать исключение")
    void checkArrayOnDigitsThrowsExceptionTest() {
        assertThatCode( () -> evaluatingService.checkArrayOnDigitsThrowException(SOME_ENTITY_NAME, new String[] {"abc", "2","3"})).isInstanceOf(EvaluatingException.class);
        assertThatCode( () -> evaluatingService.checkArrayOnDigitsThrowException(SOME_ENTITY_NAME, new String[] {"1","","3"})).isInstanceOf(EvaluatingException.class);
        assertThatCode( () -> evaluatingService.checkArrayOnDigitsThrowException(SOME_ENTITY_NAME, new String[] {"1","2",null})).isInstanceOf(EvaluatingException.class);
        assertThatCode( () -> evaluatingService.checkArrayOnDigitsThrowException(SOME_ENTITY_NAME, new String[] {"abc","",null})).isInstanceOf(EvaluatingException.class);
    }
}