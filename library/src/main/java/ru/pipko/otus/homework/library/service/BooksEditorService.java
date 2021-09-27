package ru.pipko.otus.homework.library.service;

import ru.pipko.otus.homework.library.domain.Book;

import java.util.List;

public interface BooksEditorService {

    Book addBook(String bookName, String authorId, String genreId);

    Book editBook(String bookId, String bookName, String authorId, String genreId) ;

    Book getBookById(String id);

    List<Book> getAllBooks();

    int deleteBookById(String id);

}
