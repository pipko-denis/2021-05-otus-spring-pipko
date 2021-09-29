package ru.pipko.otus.homework.library.service;

public interface EvaluatingDataService {

    boolean isThereAreOnlyDigitsInText(String text);

    boolean isTextNotNullAndNotBlank(String text);

    void checkArrayOnDigitsThrowException(String nameOfEntityForMessage, String[] ids);
}
