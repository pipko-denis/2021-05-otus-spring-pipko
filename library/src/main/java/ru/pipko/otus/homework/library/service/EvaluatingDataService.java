package ru.pipko.otus.homework.library.service;

public interface EvaluatingDataService {

    void throwExceptionIfNotOnlyDigitsInText(String text, String exceptionMessage);

    void isTextNotNullAndNotBlank(String text, String exceptionMessage);

    void checkArrayOnDigitsThrowException(String nameOfEntityForMessage, String[] ids);
}
