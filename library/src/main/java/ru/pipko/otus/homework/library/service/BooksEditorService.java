package ru.pipko.otus.homework.library.service;

import ru.pipko.otus.homework.library.domain.Book;

import java.util.List;

public interface BooksEditorService {

    Book addBook(String bookName, String authorsInline, String genreIdsInline);

    Book editBook(String bookId, String bookName, String authorsInline, String genreIdsInline) ;

    Book getBookById(String id);

    List<Book> getAllBooks();

    int deleteBookById(String id);

}
