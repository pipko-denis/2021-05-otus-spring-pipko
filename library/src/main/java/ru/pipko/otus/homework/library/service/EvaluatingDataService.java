package ru.pipko.otus.homework.library.service;

public interface EvaluatingDataService {

    void throwExceptionIfNotOnlyDigitsInText(String text, String exceptionMessage);

    boolean isTextNotNullAndNotBlank(String text);

    void checkArrayOnDigitsThrowException(String nameOfEntityForMessage, String[] ids);
}
