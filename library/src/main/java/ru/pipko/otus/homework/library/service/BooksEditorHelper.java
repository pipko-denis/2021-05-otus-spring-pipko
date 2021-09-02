package ru.pipko.otus.homework.library.service;

import ru.pipko.otus.homework.library.domain.Book;

public interface BooksEditorHelper {

    Book addBook(String bookName, String authorId, String genreId);

    Book editBook(String bookId, String bookName, String authorId, String genreId) ;

    Book getBookById(String id);

    int deleteBookById(String id);

}
