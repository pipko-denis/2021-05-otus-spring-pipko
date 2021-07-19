package ru.pipko.otus.homework.exeptions;

public class QuestionsDaoException extends Exception{

    public QuestionsDaoException(String message) {
        super(message);
    }

    public QuestionsDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
