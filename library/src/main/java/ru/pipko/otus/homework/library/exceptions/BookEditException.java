package ru.pipko.otus.homework.library.exceptions;

public class BookEditException extends Exception{

    public BookEditException(String message) {
        super(message);
    }

    public BookEditException(String message, Throwable cause) {
        super(message, cause);
    }

}
