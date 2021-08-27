package ru.pipko.otus.homework.library.service;

import ru.pipko.otus.homework.library.domain.Book;
import ru.pipko.otus.homework.library.exceptions.BookEditException;

public interface BooksEditorHelper {

    Book addBook(String bookDescription, String authorDescription, String genreDescription) throws BookEditException;

    Book editBook(String bookDescription, String authorDescription, String genreDescription) throws BookEditException;

    Book getBookByDescription(String bookDescription) throws BookEditException;

    long deleteBookByDescription(String bookDescription) throws BookEditException;

}
